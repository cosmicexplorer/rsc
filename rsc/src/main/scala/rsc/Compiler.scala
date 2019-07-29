// Copyright (c) 2017-2019 Twitter, Inc.
// Licensed under the Apache License, Version 2.0 (see LICENSE.md).
package rsc

import java.nio.file._
import rsc.classpath._
import rsc.gensym._
import rsc.input._
import rsc.lexis._
import rsc.outline._
import rsc.output._
import rsc.parse._
import rsc.pretty._
import rsc.report._
import rsc.scan._
import rsc.semanticdb._
import rsc.semantics._
import rsc.settings._
import rsc.symtab._
import rsc.syntax._
import rsc.util._

case class CachedCompiler(gensyms: Gensyms, classpath: Classpath, symtab: Symtab, infos: Infos)

object CachedCompiler {
  def firstUse(cp: Classpath): CachedCompiler = CachedCompiler(
    gensyms = Gensyms(),
    classpath = cp,
    symtab = Symtab(cp),
    infos = Infos(cp)
  )
}

class Compiler(val settings: Settings, val reporter: Reporter, cachedCompiler: CachedCompiler) extends AutoCloseable with Pretty {

  private val startInit = System.nanoTime()

  var trees: List[Source] = Nil
  val gensyms: Gensyms = cachedCompiler.gensyms
  val classpath: Classpath = cachedCompiler.classpath

  val symtab: Symtab = cachedCompiler.symtab
  val todo: Todo = Todo()
  val infos: Infos = cachedCompiler.infos
  val output: Output = Output(settings)

  private val endInit = System.nanoTime()
  private val msInit = (endInit - startInit) / 1000000
  if (settings.xprint("timings")) {
    reporter.append(VerboseMessage(s"Finished init in $msInit ms"))
  }

  def run(): Unit = {
    for ((phaseName, phaseFn) <- phases) {
      val start = System.nanoTime()
      try {
        phaseFn()
      } catch {
        case ex: Throwable =>
          reporter.append(CrashMessage(ex))
      }
      val end = System.nanoTime()
      val ms = (end - start) / 1000000
      if (settings.xprint("timings")) {
        reporter.append(VerboseMessage(s"Finished $phaseName in $ms ms"))
      }
      if (settings.xprint(phaseName)) {
        reporter.append(VerboseMessage(this.str))
      }
      if (phaseName == "parse" && settings.xprint("scan")) {
        val p = new Printer
        PrettyCompiler.xprintScan(p, this)
        reporter.append(VerboseMessage(p.toString))
      }
      if (settings.ystopAfter(phaseName)) {
        return
      }
      if (phaseName == "parse" && settings.ystopAfter("scan")) {
        return
      }
      if (reporter.problems.nonEmpty) {
        reporter.append(ErrorSummary(reporter.problems))
        return
      }
    }
  }

  def run2(): Unit = {
    DescriptorParser._cache = settings.dcache
    for ((phaseName, phaseFn) <- phases) {
      val start = System.nanoTime()
      try {
        phaseFn()
      } catch {
        case ex: Throwable =>
          reporter.append(CrashMessage(ex))
      }
      val end = System.nanoTime()
      val ms = (end - start) / 1000000
      if (settings.xprint("timings")) {
        reporter.append(VerboseMessage(s"Finished $phaseName in $ms ms"))
      }
      if (settings.xprint(phaseName)) {
        reporter.append(VerboseMessage(this.str))
      }
      if (phaseName == "parse" && settings.xprint("scan")) {
        val p = new Printer
        PrettyCompiler.xprintScan(p, this)
        reporter.append(VerboseMessage(p.toString))
      }
      if (settings.ystopAfter(phaseName)) {
        return
      }
      if (phaseName == "parse" && settings.ystopAfter("scan")) {
        return
      }
      if (reporter.problems.nonEmpty) {
        reporter.append(ErrorSummary(reporter.problems))
        return
      }
    }
  }

  private def phases: List[(String, () => Unit)] = List(
    "parse" -> (() => parse()),
    "index" -> (() => index()),
    "schedule" -> (() => schedule()),
    "outline" -> (() => outline()),
    "semanticdb" -> (() => semanticdb()),
    "scalasig" -> (() => scalasig())
  )

  private def parse(): Unit = {
    val inputs = settings.ins.map(in => Input(in))
    trees = inputs.flatMap { input =>
      if (Files.exists(input.path)) {
        if (settings.ystopAfter("scan")) {
          val scanner = Scanner(settings, reporter, input)
          while (scanner.token != EOF) {
            scanner.next()
          }
          None
        } else {
          val gensym = gensyms(input)
          val parser = Parser(settings, reporter, gensym, input)
          val tree = parser.parse()
          Some(tree)
        }
      } else {
        reporter.append(FileNotFound(input))
        None
      }
    }
  }

  private def index(): Unit = {
    val indexer = Indexer(settings, reporter, classpath, symtab, todo)
    indexer.apply()
  }

  /* TODO: seeing failures to resolve symbols such as:
    [error] util/util-test/src/test/scala/com/twitter/util/testing/ArgumentCaptureTest.scala:8: error: unbound: type ArgumentCapture
    class ArgumentCaptureTest extends FunSuite with MockitoSugar with ArgumentCapture {

     This works if we *don't* erase the rsc scalasig files! This means it can't find it in the env
     of a subsequent rsc compile! THIS MEANS WE NEED TO GET OUR CLASSPATH FROM A PREVIOUS RSC
     COMPILE INTO THE ENV BECAUSE THE OUTLINE INFOS ISN'T IT ALONE!!!
   */
  private def schedule(): Unit = {
    val scheduler = Scheduler(settings, reporter, gensyms, classpath, symtab, todo)
    trees.foreach { tree =>
      val env = Env(Root(tree.lang), Nil)
      scheduler.apply(env, tree)
    }
  }

  private def outline(): Unit = {
    val outliner = Outliner(settings, reporter, symtab)
    while (!todo.isEmpty) {
      val (env, work) = todo.remove()
      try {
        work.unblock()
        if (work.status.isPending) {
          outliner.apply(env, work)
        }
        if (work.status.isBlocked) {
          todo.add(env, work)
        }
        if (work.status.isCyclic) {
          reporter.append(IllegalCyclicReference(work))
        }
      } catch {
        case ex: Throwable =>
          val pos = work match {
            case x: ImporterScope => x.tree.pos
            case x: PackageObjectScope => x.tree.pos
            case x: TemplateScope => x.tree.pos
            case x: Sketch => x.tree.pos
            case _ => NoPosition
          }
          crash(pos, ex)
      }
    }
  }

  // Populates infos which is used in scalasig phase via Mtab
  private def semanticdb(): Unit = {
    val writer = rsc.semanticdb.Writer(settings, reporter, gensyms, symtab, infos, output)
    val outlines = symtab.outlines.result
    while (!outlines.isEmpty) {
      val outline = outlines.remove()
      try {
        writer.write(outline)
      } catch {
        case ex: Throwable =>
          crash(outline.pos, ex)
      }
    }
    writer.save()
  }

  private def scalasig(): Unit = {
    val writer = rsc.scalasig.Writer(settings, reporter, infos, output)
    val outlines = symtab.outlines.result
    val timings = new rsc.scalasig.Writer.Timings()

    while (!outlines.isEmpty) {
      val outline = outlines.remove()
      if (outline.id.sym.owner.desc.isPackage && !outline.id.sym.desc.isPackage) {
        try {
          val timings0 = writer.write(outline)
          timings.pickleTiming += timings0.pickleTiming
          timings.classfileTiming += timings0.classfileTiming
          timings.writeTiming += timings0.writeTiming
        } catch {
          case ex: Throwable =>
            crash(outline.pos, ex)
        }
      }
    }

    if (settings.xprint("timings")) {
      reporter.append(VerboseMessage(s"Finished pickle in ${timings.pickleTiming} ms"))
      reporter.append(VerboseMessage(s"Finished classfiles in ${timings.classfileTiming} ms"))
      reporter.append(VerboseMessage(s"Finished writing in ${timings.writeTiming} ms"))
    }
  }

  def close(): Unit = {
    if (settings.classpath == null) {
      classpath.close()
    }
    output.close()
  }

  def printStr(p: Printer): Unit = {
    PrettyCompiler.str(p, this)
  }

  def printRepl(p: Printer): Unit = {
    PrettyCompiler.repl(p, this)
  }

  def saveState = CachedCompiler(gensyms, classpath, symtab, infos)
}

object Compiler {
  def apply(settings: Settings, reporter: Reporter): Compiler = {
    Compiler(settings, reporter, CachedCompiler.firstUse(settings.classpath))
  }

  def apply(settings: Settings, reporter: Reporter, cachedCompiler: CachedCompiler): Compiler = {
    new Compiler(settings, reporter, cachedCompiler)
  }
}
