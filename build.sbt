ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"



libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % "42.6.0",
  "com.typesafe.slick" %% "slick" % "3.5.0-M4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.5.0-M4",

)

lazy val root = (project in file("."))
  .settings(
    name := "placedOrders2"
  )
