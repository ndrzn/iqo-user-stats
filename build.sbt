name := "iqo-user-stats"

version := "0.1"

scalaVersion := "2.12.6"

resourceDirectory in Compile := baseDirectory.value / "config"

libraryDependencies ++= Seq(
  "net.codingwell" %% "scala-guice" % "4.1.1", //Or not
  "com.typesafe.akka" %% "akka-http" % "10.1.0",
  "com.typesafe.play" %% "play-json" % "2.6.0",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
  "com.typesafe" % "config" % "1.3.3"
)


//TestKits
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.4" % "test",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.3" % Test
)
