import sbt.util

name := "iqo-user-stats"

version := "0.1.4"

scalaVersion := "2.12.6"

resourceDirectory in Compile := baseDirectory.value / "config"

val akkaVersion = "2.5.13"
val akkaHttpVersion = "10.1.3"

libraryDependencies ++= Seq(
  //DI
  "net.codingwell" %% "scala-guice" % "4.1.1",
  //DD
  "com.typesafe.slick" %% "slick" % "3.2.3",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.0",
  "org.postgresql" % "postgresql" % "9.4.1212",
  //InMemory
  "org.cache2k" % "cache2k-api" % "1.0.2.Final",
  "org.cache2k" % "cache2k-all" % "1.0.2.Final",
  //Akka
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  //Json
  "com.typesafe.play" %% "play-json" % "2.6.0",
  //Logging
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"
)

//TestKits
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.4" % "test",
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test
)

sources in (Compile, doc) := Seq()

logLevel := util.Level.Info


//Assembly settigns
import sbtassembly.AssemblyPlugin.defaultUniversalScript

logLevel in assembly := util.Level.Info
test in assembly := {}

assemblyOption in assembly := (assemblyOption in assembly).value.copy(prependShellScript = Some(defaultUniversalScript()))

assemblyJarName in assembly := s"${name.value}-${version.value}"
