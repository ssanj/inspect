import sbt._

lazy val scala212 = "2.12.5"
lazy val scala213 = "2.13.0"

lazy val supportedScalaVersions = List(scala212, scala213)

lazy val commonSettings = Seq(
  crossScalaVersions := supportedScalaVersions,
  organization := "net.ssanj",
  version := "3.0.0",
  scalacOptions ++= scalacSettings.value
)

lazy val commonScalacSettings: Seq[String] = Seq (
  "-deprecation",
  "-feature"
 )

lazy val scala212ScalaCSettings: Seq[String] = Seq(
  "-Ywarn-dead-code",
  "-Ywarn-inaccessible",
  "-Ywarn-unused-import"
)

lazy val scala213ScalacSettings = Seq.empty[String]


lazy val scalaReflect = Def.setting { "org.scala-lang" % "scala-reflect" % scalaVersion.value }

lazy val scalacSettings = Def.setting {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, 12))=> commonScalacSettings ++ scala212ScalaCSettings
    case Some((2, 13))=> commonScalacSettings ++ scala213ScalacSettings
    case _ => Seq.empty[String]
  }
}

lazy val root = (project in file("."))
  .aggregate(zen, core)
  .settings(
    // crossScalaVersions must be set to Nil on the aggregating project
    crossScalaVersions := Nil,
    scalaVersion := scala212,
    publishLocal / skip := true,
    publish / skip := true
  )

lazy val core = (project in file("core"))
  .dependsOn(zen)
  .settings(
    commonSettings,
    publishLocal / skip := true,
    publish / skip := true
  )

lazy val zen = (project in file("macro"))
  .settings(
    commonSettings,
    licenses ++= Seq(("MIT", url("http://opensource.org/licenses/MIT"))),
    libraryDependencies ++= Seq(
    scalaReflect.value
  )
)