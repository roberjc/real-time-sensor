name := "tfm"

version := "0.1"

scalaVersion := "2.11.11"

val sparkVersion = "2.3.0"


libraryDependencies ++= Seq(
  "org.scalaj" % "scalaj-http_2.11" % "2.3.0",
  "org.apache.spark" % "spark-core_2.11" % sparkVersion,
  "org.apache.spark" % "spark-sql_2.11" % sparkVersion,
  "com.typesafe.play" % "play-json_2.11" % "2.4.6"
)