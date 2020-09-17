name := "ScalaBeelity"

version := "0.1"

scalaVersion := "2.13.3"

scalaSource in Test := baseDirectory.value / "src" / "test"

scalaSource in Compile := baseDirectory.value / "src" / "main"

libraryDependencies ++= Seq(

  "it.unibo.alice.tuprolog" % "tuprolog" % "3.3.0",

  // Last stable release
  "org.scalanlp" %% "breeze" % "1.1",

  // TODO!! [...] It also packages various blas implementations, which have licenses that may or may not
  // be compatible with the Apache License. No GPL code, as best I know.
  "org.scalanlp" %% "breeze-natives" % "1.1",

  // The visualization library is distributed separately as well.
  // It depends on LGPL code
  "org.scalanlp" %% "breeze-viz" % "1.1",

  "org.scala-lang.modules" %% "scala-parallel-collections" % "0.2.0",

  "com.github.haifengl" % "smile-core" % "2.5.1",

  "com.github.haifengl" % "smile-plot" % "2.5.1",

  "com.github.haifengl" % "smile-math" % "2.5.1",

  "com.github.haifengl" % "smile-data" % "2.5.1",

  "com.github.haifengl" % "smile-netlib" % "2.4.0",

  "com.github.haifengl" % "smile-nlp" % "2.5.1",

  "com.github.haifengl" % "smile-io" % "2.5.1",

  "com.github.haifengl" % "smile-graph" % "2.5.1",

  "com.github.haifengl" %% "smile-scala" % "2.5.1",

  // https://mvnrepository.com/artifact/org.scalatest/scalatest
  "org.scalatest" %% "scalatest" % "3.2.0" % Test
)

scalacOptions ++= Seq("-language:postfixOps", "-language:implicitConversions")