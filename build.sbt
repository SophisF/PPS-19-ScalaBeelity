name := "ScalaBeelity"

version := "0.1"

scalaVersion := "2.13.3"




libraryDependencies ++= Seq(

  "it.unibo.alice.tuprolog" % "tuprolog" % "3.3.0",

  "org.jfree" % "jfreechart" % "1.0.14",

  // https://mvnrepository.com/artifact/org.openjfx/javafx-base
  "org.openjfx" % "javafx-base" % "16-ea+1",

  // https://mvnrepository.com/artifact/org.openjfx/javafx-controls
  "org.openjfx" % "javafx-controls" % "16-ea+1",


  // https://mvnrepository.com/artifact/org.openjfx/javafx-graphics
  "org.openjfx" % "javafx-graphics" % "16-ea+1",

  // https://mvnrepository.com/artifact/org.openjfx/javafx-media
  "org.openjfx" % "javafx-media" % "16-ea+1",

  // https://mvnrepository.com/artifact/org.scalafx/scalafx
  "org.scalafx" %% "scalafx" % "14-R19",

  "com.github.haifengl" % "smile-core" % "2.5.1",

  "com.github.haifengl" % "smile-plot" % "2.5.1",

  "com.github.haifengl" % "smile-math" % "2.5.1",

  "com.github.haifengl" % "smile-data" % "2.5.1",

  "com.github.haifengl" % "smile-netlib" % "2.4.0",

  "com.github.haifengl" % "smile-nlp" % "2.5.1",

  "com.github.haifengl" % "smile-io" % "2.5.1",

  "com.github.haifengl" % "smile-graph" % "2.5.1",

  "com.github.haifengl" %% "smile-scala" % "2.5.1",

  // Last stable release
  "org.scalanlp" %% "breeze" % "1.1",

  // TODO!! [...] It also packages various blas implementations, which have licenses that may or may not
  // be compatible with the Apache License. No GPL code, as best I know.
  "org.scalanlp" %% "breeze-natives" % "1.1",

  // The visualization library is distributed separately as well.
  // It depends on LGPL code
  "org.scalanlp" %% "breeze-viz" % "1.1",

  // https://mvnrepository.com/artifact/org.scalatest/scalatest
  "org.scalatest" %% "scalatest" % "3.2.0" % Test
)

scalacOptions ++= Seq("-language:postfixOps", "-language:implicitConversions")
