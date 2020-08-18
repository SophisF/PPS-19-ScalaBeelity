name := "ScalaBeelity"

version := "0.1"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(

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

  "com.github.piotr-kalanski" % "splot" % "0.2.0"

)
