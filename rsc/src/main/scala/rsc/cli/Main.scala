// Copyright (c) 2017-2018 Twitter, Inc.
// Licensed under the Apache License, Version 2.0 (see LICENSE.md).
package rsc.cli

import java.nio.charset.StandardCharsets.UTF_8
import java.nio.file._
import spray.json._
import rsc.Compiler
import rsc.syntax._
import rsc.pretty._
import rsc.report._
import rsc.settings._

object Main {
  import TreesProtocol._

  def main(args: Array[String]): Unit = {
    val result = process(args)
    if (result) sys.exit(0) else sys.exit(1)
  }

  def process(args: Array[String]): Boolean = {
    val expandedArgs = {
      args.toList.flatMap { arg =>
        if (arg.startsWith("@")) {
          val argPath = Paths.get(arg.substring(1))
          val argText = new String(Files.readAllBytes(argPath), UTF_8)
          argText.split(EOL).map(_.trim).filter(_.nonEmpty).toList
        } else {
          List(arg)
        }
      }
    }
    Settings.parse(expandedArgs) match {
      case Some(settings) =>
        val reporter = ConsoleReporter(settings)
        val compiler = Compiler(settings, reporter)
        try {
          if ((settings.toParsedOutputFile.isEmpty) && (settings.fromParsedOutputFile.isEmpty)) {
            val parseTrees: List[Source] = compiler.parseSerialized()
            compiler.acceptParsedTrees(parseTrees)
            compiler.run()
          } else if (!settings.toParsedOutputFile.isEmpty) {
            val outFile = settings.toParsedOutputFile.get
            if (!settings.fromParsedOutputFile.isEmpty) ???
            val absolutePath = outFile.toAbsolutePath
            Files.createDirectories(absolutePath.getParent)
            val parseTrees: List[Source] = compiler.parseSerialized()
            Files.write(absolutePath, PrettyPrinter(parseTrees.toJson).getBytes)
          } else {
            if (settings.fromParsedOutputFile.isEmpty) ???
            val inFile = settings.fromParsedOutputFile.get
            val absolutePath = inFile.toAbsolutePath
            val parseTrees: List[Source] =
              (new String(Files.readAllBytes(absolutePath))).parseJson.convertTo[List[Source]]
            compiler.acceptParsedTrees(parseTrees)
            compiler.run()
          }
          reporter.problems.isEmpty
        } finally {
          compiler.close()
        }
      case None =>
        false
    }
  }
}
