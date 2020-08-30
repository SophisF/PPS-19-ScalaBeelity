name := "ScalaBeelity"

version := "0.1"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(

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

  "com.github.haifengl" %% "smile-scala" % "2.5.1"
)