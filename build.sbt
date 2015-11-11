name := "play-scala"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test,
  "com.orientechnologies" % "orientdb-core" % "2.1.3",
  "com.orientechnologies" % "orientdb-graphdb" % "2.1.3",
  "com.orientechnologies" % "orientdb-client" % "2.1.3",
  "com.tinkerpop.blueprints" % "blueprints-core" % "2.6.0",
  "javax.persistence" % "persistence-api" % "1.0.2"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

javaOptions in Test += "-Dconfig.file=conf/test.conf"