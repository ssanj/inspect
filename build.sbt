lazy val commonSettings = Seq(
  scalaVersion := "2.12.5",
  organization := "net.ssanj",
  version := "2.2.0",
  scalacOptions ++= Seq(
                        "-deprecation",
                        "-feature",
                        "-Ywarn-dead-code",
                        "-Ywarn-inaccessible",
                        "-Ywarn-unused-import"
                       )
)

lazy val scalaReflect = Def.setting { "org.scala-lang" % "scala-reflect" % scalaVersion.value }

lazy val core = (project in file("core"))
  .dependsOn(zen)
  .settings(
    commonSettings,
    libraryDependencies ++= Seq(
      "io.monix" %% "monix" % "3.0.0-RC1"
    ),
    publish := {},
    publishLocal := {}
  )

lazy val zen = (project in file("macro"))
  .settings(
    commonSettings,
    licenses ++= Seq(("MIT", url("http://opensource.org/licenses/MIT"))),
    libraryDependencies ++= Seq(
    scalaReflect.value,
    "org.scalatest"  %% "scalatest"   % "3.0.1"  % "test"
  )
)