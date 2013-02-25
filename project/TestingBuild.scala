import _root_.net.virtualvoid.sbt.graph.Plugin._
import sbt._
import sbt.Keys._
import sbtassembly.Plugin._
import sbtrelease.ReleasePlugin._

object TestingBuild extends Build {

  //Resolvers
  lazy val commonResolvers = Seq(
    "Codahale Repo" at "http://repo.codahale.com",
    "Sonatype Repo" at "https://oss.sonatype.org/content/repositories/releases/",
    "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
    "Excilys" at "http://repository.excilys.com/content/groups/public",
    "sbt-idea-repo" at "http://mpeltonen.github.com/maven/",
    Resolver.url("artifactory", url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)
  )

  //Build Settings applied to all projects
  lazy val commonBuildSettings = Seq(
    organization := "com.mindcandy.testing",
    scalaVersion := "2.9.2",
    resolvers ++= commonResolvers
  )

  //Settings applied to all projects
  lazy val defaultSettings = Defaults.defaultSettings ++ assemblySettings ++ commonBuildSettings ++ releaseSettings ++ Seq(
    libraryDependencies ++= dependencies,
    fork in test := true,  //Fork a new JVM for running tests
    parallelExecution in Test := false,
    javaOptions in (Test,run) += "-XX:MaxPermSize=128m"
  )

  lazy val dependencies = Seq(
    "org.specs2" %% "specs2" % "1.12.2" % "test",
    "net.databinder" %% "unfiltered-netty" % "0.6.7",
    "net.databinder" %% "unfiltered" % "0.6.7",
    "net.databinder" %% "unfiltered-netty-server" % "0.6.7",
    "net.databinder.dispatch" %% "dispatch-core" % "0.9.5"
  )

  //Main project configuration
  lazy val root = Project(
    id = "testing",
    base = file("."),
    settings = defaultSettings
  ) .settings(graphSettings: _*)

}
