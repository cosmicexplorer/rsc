// Copyright (c) 2017-2019 Twitter, Inc.
// Licensed under the Apache License, Version 2.0 (see LICENSE.md).
package rsc.output

import java.io._
import java.nio.file._
import java.util.jar._
import java.util.zip.Deflater._
import rsc.report._
import rsc.settings._

sealed trait Output extends AutoCloseable {
  def write(path: Path, bytes: Array[Byte]): Unit
}

class DirectoryOutput(settings: Settings) extends Output {
  def write(path: Path, bytes: Array[Byte]): Unit = {
    val absolutePath = settings.d.resolve(path).toAbsolutePath
    Files.createDirectories(absolutePath.getParent)
    Files.write(absolutePath, bytes)
  }

  def close(): Unit = {
    ()
  }
}

class JarOutput(settings: Settings) extends Output {
  private var jos: JarOutputStream = _

  private def ensureStream(): Unit = {
    if (jos != null) return
    Files.createDirectories(settings.d.toAbsolutePath.getParent)
    val os = Files.newOutputStream(settings.d)
    val bos = new BufferedOutputStream(os)
    jos = new JarOutputStream(bos)
    jos.setLevel(NO_COMPRESSION)
  }

  def write(path: Path, bytes: Array[Byte]): Unit = {
    ensureStream()
    jos.putNextEntry(new JarEntry(path.toString))
    jos.write(bytes)
    jos.closeEntry()
  }

  def close(): Unit = {
    if (jos == null) return
    jos.close()
  }
}

case class NormalizedPathForCaching private (path: Path) {
  def toPath: Path = path
}
object NormalizedPathForCaching {
  def apply(path: Path): NormalizedPathForCaching = new NormalizedPathForCaching(path.normalize)
}

trait InMemoryOutputCache {
  def keysAsStr: String
  def findPath(path: NormalizedPathForCaching): Option[Array[Byte]]
  def assignPath(path: NormalizedPathForCaching, bytes: Array[Byte])(implicit reporter: Reporter): Unit
}

class InMemoryOutput(cache: InMemoryOutputCache, reporter: Reporter) extends Output {

  override def write(path: Path, bytes: Array[Byte]): Unit =
    cache.assignPath(NormalizedPathForCaching(path), bytes)(reporter)

  override def close(): Unit = ()
}

private[rsc] class SingletonOutputCache extends InMemoryOutputCache {
  import scala.collection.JavaConverters._
  import java.util.concurrent.ConcurrentHashMap

  private val inMemoryScalasigs: ConcurrentHashMap[NormalizedPathForCaching, Array[Byte]] = new ConcurrentHashMap

  override def keysAsStr: String = {
    val joined = inMemoryScalasigs.asScala.keys.map(_.toPath.toString).mkString(", ")
    s"[$joined]"
  }

  override def findPath(path: NormalizedPathForCaching): Option[Array[Byte]] =
    Option(inMemoryScalasigs.get(path))

  override def assignPath(path: NormalizedPathForCaching, bytes: Array[Byte])(
    implicit reporter: Reporter
  ): Unit = findPath(path) match {
    case Some(x) => throw new Exception(s"should only write file $path once! first $x, now $bytes!")
    case None =>
      reporter.append(VerboseMessage(s"classfile assigned: $path, $bytes, $this"))
      inMemoryScalasigs.putIfAbsent(path, bytes)
      ()
  }
}

object SingletonOutputCache {
  def get: SingletonOutputCache = StaticCache.getCache
}

class ComposedOutput(outputs: Output*) extends Output {
  override def write(path: Path, bytes: Array[Byte]): Unit = outputs.foreach(_.write(path, bytes))
  override def close(): Unit = outputs.foreach(_.close())
}

object Output {
  def apply(settings: Settings, cache: InMemoryOutputCache)(implicit reporter: Reporter): Output = {
    val filesystemOutput = if (settings.d.toString.endsWith(".jar")) {
      new JarOutput(settings)
    } else {
      new DirectoryOutput(settings)
    }
    val inMemoryOutput = new InMemoryOutput(cache, reporter)
    settings.memoryLocations match {
      case Filesystem => filesystemOutput
      case InMemory => inMemoryOutput
      case MemoryAndFilesystem => new ComposedOutput(filesystemOutput, inMemoryOutput)
    }
  }
}
