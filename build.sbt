import Dependencies._

import scalariform.formatter.preferences._


name := "scala-reactivemongo"

lazy val commonSettings = Seq(
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.11.6",
  scalacOptions := Seq(
    "-unchecked",
    "-deprecation",
    "-encoding", "utf8",
    "-feature",
    "-language:higherKinds",
    "-language:postfixOps",
    "-language:implicitConversions",
    "-language:existentials",
    "-target:jvm-1.6"
  ),
  parallelExecution in Test := true,
  javaOptions in Test ++= Seq("-Xmx512m", "-XX:MaxPermSize=512m"),
  ScalariformKeys.preferences := ScalariformKeys.preferences.value
    .setPreference(AlignParameters, true)
    .setPreference(DoubleIndentClassDeclaration, true)
    .setPreference(MultilineScaladocCommentsStartOnFirstLine, true)
    .setPreference(PlaceScaladocAsterisksBeneathSecondAsterisk, true)
)

lazy val settings = (
  commonSettings
  ++ scalariformSettings
  ++ org.scalastyle.sbt.ScalastylePlugin.Settings
  )

lazy val root = project.in(file("."))
  .settings(settings: _*)
  .settings(
    libraryDependencies ++= appDefs
  )
