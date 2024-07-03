val scala3Version = "3.4.2"
val zioVersion = "2.1.5-RC6"

lazy val root = project
  .in(file("."))
  .settings(
    name := "graph-scala",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0" % Test,
      // "dev.zio" %% "zio" % zioVersion,                 // ZIO core library
      // "dev.zio" %% "zio-json" % "0.4.2",               // ZIO JSON library
      // "dev.zio" %% "zio-logging" % "2.1.14",           // ZIO logging
      // "dev.zio" %% "zio-logging-slf4j" % "2.1.14",     // ZIO logging SLF4J backend
      "org.scalatest" %% "scalatest" % "3.2.19" % Test, // ScalaTest core library
      "org.scalatest" %% "scalatest-flatspec" % "3.2.19" % Test,
    ),
    scalacOptions ++= Seq(
      "-explain",
      "-explain-cyclic"
    )
  )
