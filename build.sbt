name := "ScalaBeelity"

version := "0.1"

scalaVersion := "2.13.3"

scalaSource in Test := baseDirectory.value / "src" / "test"

scalaSource in Compile := baseDirectory.value / "src" / "main"

libraryDependencies ++= Seq(

  "it.unibo.alice.tuprolog" % "tuprolog" % "3.3.0",

  "org.scalanlp" %% "breeze-viz" % "1.1",

  "org.scala-lang.modules" %% "scala-parallel-collections" % "0.2.0",

  "org.scalatest" %% "scalatest" % "3.2.0" % Test
)

scalacOptions ++= Seq("-language:postfixOps", "-language:implicitConversions")