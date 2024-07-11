val scala3Version = "3.4.2"
val zioVersion = "2.1.5"

lazy val core = project
  .in(file("core"))
  .settings(
    name := "graph-scala-core",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0" % Test,
      "org.scalatest" %% "scalatest" % "3.2.19" % Test, // ScalaTest core library
      "org.scalatest" %% "scalatest-flatspec" % "3.2.19" % Test,
    ),
    scalacOptions ++= Seq(
      "-explain",
      "-explain-cyclic"
    )
  )

lazy val app = project
  .in(file("app"))
  .settings(
    name := "graph-scala-app",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % zioVersion,
      "dev.zio" %% "zio-json" % "0.4.2",
      "dev.zio" %% "zio-logging" % "2.1.14",
      "dev.zio" %% "zio-logging-slf4j" % "2.1.14",
      "dev.zio" %% "zio-cli" % "0.4.0", // For CLI-based applications      
      "org.scalatest" %% "scalatest" % "3.2.19" % Test, // ScalaTest core library
      "org.scalatest" %% "scalatest-flatspec" % "3.2.19" % Test,
    ),
    scalacOptions ++= Seq(
      "-explain",
      "-explain-cyclic"
    )
  )
  .dependsOn(core)

lazy val root = project
  .in(file("."))
  .aggregate(core, app)
  .settings(
    run := (app/Compile/run).evaluated
  )
