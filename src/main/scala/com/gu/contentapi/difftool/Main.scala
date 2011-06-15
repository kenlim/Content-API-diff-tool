package com.gu.contentapi.difftool

import io.Source

import dispatch._
import OwenDiff.{Diff => OwensDiff}
import net.liftweb.json._
import java.io.{FileWriter, File, StringWriter}
import net.liftweb.json.JsonAST.JValue
import xml.XML
import com.google.xmldiff.{NoDiff, Comparison, Diff => XmlDiff}

object Main {
//  val masterContentApiHost = url("http://localhost:8080/api")
//  val liftRestContentApiHost = url("http://localhost:8700/content-api/api")

  val masterContentApiHost = url("http://content.guardianapis.com")
  val liftRestContentApiHost = url("http://content.guardianapis.com/search  ")


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

  def doJsonDiff(path: String, master: String, liftRest: String) = {
    val (masterJson, liftJson) = (parse(master), parse(liftRest))
    val Diff(changed, added, deleted) = masterJson diff liftJson

    """|%s
        |
       |LINES MODIFIED:
       |%s

       |LINES ADDED:
       |%s

       |LINES DELETED:
       |%s
    |""".stripMargin.format(path, r(changed), r(added), r(deleted))
  }

  def doXmlDiff(path: String, master: String, liftRest: String) = {
    val comparison = new Comparison

    comparison(XML.loadString(master), XML.loadString(liftRest)) match {
      case NoDiff => "Documents are similar."
      case diff   => diff.toString
    }
  }

  def diffResult(pathAndParams: String, master: String, liftRest: String) = {
       master.charAt(0) match {
         case '<' => doXmlDiff(pathAndParams, master, liftRest)
         case _ => doJsonDiff(pathAndParams, master, liftRest)
       }
  }

  def processline( idxAndLine: (String, Int) ) {
    val (line, idx) = idxAndLine
    val pathAndParams = line.replaceAllLiterally("/content-api/api", "")

    // Translate the URLs into calls to make to the:
    // 1. Master Content Api
    val masterResponse = getResponse(masterContentApiHost / pathAndParams)
    // 2. Lift-rest Content Api
    val liftRestResponse = getResponse(liftRestContentApiHost / pathAndParams)

    writeToFile(new File("result/%d.diff" format idx), diffResult(pathAndParams, masterResponse, liftRestResponse))
  }

  def main(args: Array[String]) {
    new File("result").mkdirs()

    // Load up the provided log file
    val logFile = Source.fromFile("support/inputFile").getLines().take(10)

    logFile.zipWithIndex.toList.par.foreach(processline)
    
    h.shutdown()
  }
}