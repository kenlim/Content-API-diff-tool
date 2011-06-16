name := "Content Api Diff Tool"

version := "1.0"

libraryDependencies ++= Seq(
    "net.databinder" %% "dispatch-http" % "0.8.3",
    "net.liftweb" %% "lift-json" % "2.4-M2",
    "org.scalatest" % "scalatest_2.9.0" % "1.6.1"
    )

scalaVersion := "2.9.0-1"
