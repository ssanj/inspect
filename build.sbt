lazy val commonSettings = Seq(
  scalaVersion := "2.12.4",
  organization := "net.ssanj.inspect",
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
  .dependsOn(macroSub)
  .settings(
    commonSettings,
    libraryDependencies ++= Seq(
      "io.monix" %% "monix" % "3.0.0-RC1"
    )
    // other settings here
  )

lazy val macroSub = (project in file("macro"))
  .settings(
    commonSettings,
    libraryDependencies ++= Seq(
    scalaReflect.value,
    "org.scalatest"  %% "scalatest"   % "3.0.1"  % "test",
    "org.scalacheck" %% "scalacheck"  % "1.13.5" % "test"
  )
)