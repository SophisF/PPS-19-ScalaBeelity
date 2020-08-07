name := "ScalaBeelity"

version := "0.1"

scalaVersion := "2.13.3"

libraryDependencies  ++= Seq(
  // Last stable release
  "org.scalanlp" %% "breeze" % "1.1",

  // TODO!! [...] It also packages various blas implementations, which have licenses that may or may not
  // be compatible with the Apache License. No GPL code, as best I know.
  "org.scalanlp" %% "breeze-natives" % "1.1",

  // The visualization library is distributed separately as well.
  // It depends on LGPL code
  "org.scalanlp" %% "breeze-viz" % "1.1",

  // https://mvnrepository.com/artifact/it.unibo.alice.tuprolog/tuprolog
  "it.unibo.alice.tuprolog" % "tuprolog" % "3.3.0"
)