val dottyVersion = "0.10.0-RC1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "idea",
    version := "0.1.0",
    mainClass := Some("App"),

    scalaVersion := dottyVersion,

    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"
  )