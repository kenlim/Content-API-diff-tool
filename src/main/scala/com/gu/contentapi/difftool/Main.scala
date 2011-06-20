package com.gu.contentapi.difftool

import io.Source

import dispatch._
import OwenDiff.{Diff => OwensDiff}
import net.liftweb.json._
import java.io.{FileWriter, File, StringWriter}
import net.liftweb.json.JsonAST.JValue
import com.google.xmldiff.{NoDiff, Comparison, Diff => XmlDiff}
import xml.{Elem, Node, XML}
import xml.transform.{RuleTransformer, RewriteRule}

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

  def cannonicalizeXml(xml: String) = {
    import sys.process._

    val tmpFile = File.createTempFile("abc", "xml")


    object FieldSorter extends RewriteRule {
      override def transform(n: Node) = n match {
        case Elem(prefix, "fields", attribs, scope, children@_*) =>
          val sortedChildren = children.sortBy(_.attribute("name").map(_.text).getOrElse(""))
          Elem(prefix, "fields", attribs, scope, sortedChildren: _*)
        case other => other
      }
    }

    val transfomer = new RuleTransformer(FieldSorter)

    writeToFile(tmpFile, transfomer(XML.loadString(xml)).toString())

    val cmd = ("xmllint --c14n " + tmpFile.getCanonicalPath) #| "xmllint --format -"
    cmd.!!
  }

  def doXmlDiff(path: String, master: String, liftRest: String) = {
    val c14nMaster = cannonicalizeXml(master)
    val c14nLift = cannonicalizeXml(liftRest)

    OwensDiff.diff(c14nMaster.split("\n"), c14nLift.split("\n")).mkString
  }

  def diffResult(pathAndParams: String, master: String, liftRest: String) = {
    master.charAt(0) match {
      case '<' => doXmlDiff(pathAndParams, master, liftRest)
      case _ => doJsonDiff(pathAndParams, master, liftRest)
    }
  }

  def processline(idxAndLine: (String, Int)) {
    val (line, idx) = idxAndLine
    val pathAndParams = line.replaceAllLiterally("/content-api/api", "")

    try {
      // Translate the URLs into calls to make to the:
      // 1. Master Content Api
      val masterResponse = getResponse(masterContentApiHost / pathAndParams)
<<<<<<< HEAD

      // 2. Lift-rest Content Api
      val liftRestResponse = getResponse(liftRestContentApiHost / pathAndParams)

      //writeToFile(new File("result/%d.diff" format idx), diffResult(pathAndParams, masterResponse, liftRestResponse))
      println(diffResult(pathAndParams, masterResponse, liftRestResponse))
    } catch {
      case sc: StatusCode => println("Master returned %s for %s".format(sc.code, pathAndParams))
=======
      // 2. Lift-rest Content Api
      val liftRestResponse = getResponse(liftRestContentApiHost / pathAndParams)

      writeToFile(new File("result/%d.diff" format idx), diffResult(pathAndParams, masterResponse, liftRestResponse))
    } catch {
      case r: RuntimeException => println("problems getting %s".format(pathAndParams))
>>>>>>> c8baf0ffee28db2efdff7d2dbd14ab7c77859702
    }
  }

  def main(args: Array[String]) {
    new File("result").mkdirs()

    // Load up the provided log file
    val logFile = Source.fromFile("support/inputFile").getLines().take(100)

    logFile.zipWithIndex.toList.par.foreach(processline)

    h.shutdown()
  }
}
