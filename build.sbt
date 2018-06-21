name := "iqo-user-stats"

version := "0.1.3"

scalaVersion := "2.12.6"

resourceDirectory in Compile := baseDirectory.value / "config"

val akkaVersion = "2.5.13"
val akkaHttpVersion = "10.1.3"

libraryDependencies ++= Seq(
  "net.codingwell" %% "scala-guice" % "4.1.1",
  "com.typesafe.slick" %% "slick" % "3.2.3",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.0",
  "org.postgresql" % "postgresql" % "9.4.1212",
  "org.cache2k" % "cache2k-api" % "1.0.2.Final",
  "org.cache2k" % "cache2k-all" % "1.0.2.Final",
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.play" %% "play-json" % "2.6.0",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"
)


//TestKits
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.4" % "test",
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test
)