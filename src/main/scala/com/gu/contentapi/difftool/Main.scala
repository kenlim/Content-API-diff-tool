package com.gu.contentapi.difftool

import io.Source

import OwenDiff.Diff
import dispatch._
import java.io.{FileWriter, File, StringWriter}

object Main {
  val masterContentApiHost = url("http://localhost:8080/api")
  val liftRestContentApiHost = url("http://localhost:8700/content-api/api")

  val h = new Http with thread.Safety


  def writeToFile(file: File, s: String) {
    val writer = new FileWriter(file)
    writer.write(s)
    writer.close()
  }

  def getResponse(req: Request) = {
    val myReq = req <:< Map("Accept" -> "application/json")
    h(myReq as_str)
  }

  def doTextDiff(master: String, liftRest: String) = {
    def tidy(s: String) = s.replaceAllLiterally("<", "\n<").split('\n')
    Diff.diff(tidy(master), tidy(liftRest))
  }

  def processline( idxAndLine: (String, Int) ) {
    val (line, idx) = idxAndLine
    val pathAndParams = line.replaceAllLiterally("/content-api/api", "")

    // Translate the URLs into calls to make to the:
    // 1. Master Content Api
    val masterResponse = getResponse(masterContentApiHost / pathAndParams)
    // 2. Lift-rest Content Api
    val liftRestResponse = getResponse(liftRestContentApiHost / pathAndParams)

    val diffResult = doTextDiff(masterResponse, liftRestResponse)

    writeToFile(new File("result/%d.diff" format idx), diffResult.mkString)
  }

  def main(args: Array[String]) {
    new File("result").mkdirs()

    // Load up the provided log file
    val logFile = Source.fromFile("support/inputFile").getLines().take(10)

    logFile.zipWithIndex.toList.par.foreach(processline)
    
    h.shutdown()
  }
}