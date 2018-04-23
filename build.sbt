lazy val commonSettings = Seq(
  scalaVersion := "2.12.5",
  organization := "net.ssanj",
  version := "2.0.0",
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
  .dependsOn(inspect)
  .settings(
    commonSettings,
    libraryDependencies ++= Seq(
      "io.monix" %% "monix" % "3.0.0-RC1"
    ),
    publish := {},
    publishLocal := {}
  )

lazy val inspect = (project in file("macro"))
  .settings(
    commonSettings,
    libraryDependencies ++= Seq(
    scalaReflect.value,
    "org.scalatest"  %% "scalatest"   % "3.0.1"  % "test"
  )
)