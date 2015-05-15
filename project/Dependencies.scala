import sbt._

object Dependencies {

  val reactiveMongo = "org.reactivemongo" %% "reactivemongo-extensions-bson" % "0.10.5.0.0.akka23"

  val scalaTest = "org.scalatest" %% "scalatest" % "2.2.4"

  val appDefs = Seq (reactiveMongo, scalaTest % Test)

}
