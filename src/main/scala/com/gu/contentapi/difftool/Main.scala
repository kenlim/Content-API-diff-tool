package com.gu.contentapi.difftool

import io.Source
import java.net.URL
import dispatch.{url, Http}
import javax.management.remote.rmi._RMIConnection_Stub
import OwenDiff.Diff

object Main {
  val masterContentApiHost = "http://localhost:8080/api"
  val liftRestContentApiHost = "http://localhost:8700/content-api/api"

  val h = new Http

  def processline(line: String) {
    var pathAndParams = line.replaceAllLiterally("/content-api/api", "")

    if (! pathAndParams.matches(".*(json|xml).*")) {
      pathAndParams += "&format=json"
    }
    
    // Translate the URLs into calls to make to the:
    // 1. Master Content Api
    val masterResponse = h(url(masterContentApiHost + pathAndParams) as_str).replaceAllLiterally("<", "\n<").split('\n')
    // 2. Lift-rest Content Api
    val liftRestResponse = h(url(liftRestContentApiHost + pathAndParams) as_str).replaceAllLiterally("<", "\n<").split('\n')


    val diffList = Diff.diff(masterResponse, liftRestResponse)

    println(diffList.mkString)
  }

  def main(args: Array[String]) = {
    // Load up the provided log file
    val logFile = Source.fromFile("support/inputFile").getLines().take(10)

    logFile.foreach(processline _)

  }
}