ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

libraryDependencies += "org.postgresql" % "postgresql" % "42.6.0"

lazy val root = (project in file("."))
  .settings(
    name := "placedOrders2"
  )
