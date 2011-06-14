package com.gu.contentapi.difftool

import io.Source

import OwenDiff.{ Diff => OwensDiff }
import dispatch._
import net.liftweb.json._
import java.io.{FileWriter, File, StringWriter}
import net.liftweb.json.JsonAST.JValue

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
    OwensDiff.diff(tidy(master), tidy(liftRest))
  }

  def r(json: JValue) = json match {
    case JNothing => "No difference"
    case other => pretty(render(other))
  }

  def doJsonDiff(master: String, liftRest: String) = {
    val (masterJson, liftJson) = (parse(master), parse(liftRest))
    val Diff(changed, added, deleted) = masterJson diff liftJson

    """|LINES MODIFIED:
       |%s

       |LINES ADDED:
       |%s

       |LINES DELETED:
       |%s
    |""".stripMargin.format(r(changed), r(added), r(deleted))
  }

  def processline( idxAndLine: (String, Int) ) {
    val (line, idx) = idxAndLine
    val pathAndParams = line.replaceAllLiterally("/content-api/api", "")

    // Translate the URLs into calls to make to the:
    // 1. Master Content Api
    val masterResponse = getResponse(masterContentApiHost / pathAndParams)
    // 2. Lift-rest Content Api
    val liftRestResponse = getResponse(liftRestContentApiHost / pathAndParams)


    val diffResult =
      """|Differences in results when calling
         |%s
         
      |""".stripMargin.format(pathAndParams) + doJsonDiff(masterResponse, liftRestResponse)

    writeToFile(new File("result/%d.diff" format idx), diffResult.mkString)
  }

  def main(args: Array[String]) {
    new File("result").mkdirs()

    // Load up the provided log file
    val logFile = Source.fromFile("support/inputJson").getLines().take(10)

    logFile.zipWithIndex.toList.par.foreach(processline)
    
    h.shutdown()
  }
}