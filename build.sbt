name := "worldlineddu"
version := "0.1"
scalaVersion := "2.13.6"
val akkaVersion = "2.6.15"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "commons-io" % "commons-io" % "2.10.0",
  "org.slf4j" % "slf4j-api" % "1.7.31",
  "org.scalatest" %% "scalatest" % "3.2.9"
)
