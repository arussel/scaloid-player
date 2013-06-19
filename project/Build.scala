import sbt._
import Keys._
import AndroidKeys._

object BuildSettings {
  val androidVerCd = 0
  lazy val basicSettings = Seq(
    version               := "1.0." + androidVerCd,
    organization          := "com.scaloidplayer",
    scalaVersion          := "2.10.1",
    resolvers             ++= Dependencies.resolutionRepos,
    scalacOptions         := Seq(
      "-encoding", "utf8",
      "-target:jvm-1.6"
    ),
    javacOptions          ++= Seq(
      "-encoding", "utf8",
      "-source", "1.6",
      "-target", "1.6")
  )

}

object Dependencies {
  val resolutionRepos = Seq(
    "typesafe releases" at "http://repo.typesafe.com/typesafe/releases",
    "typesafe snapshots" at "http://repo.typesafe.com/typesafe/snapshots"
  )

  def compile   (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "compile")
  def provided  (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "provided")
  def test      (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "test")
  def runtime   (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "runtime")
  def container (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "container")

  val scaloid = "org.scaloid" %% "scaloid" % "2.0-8"
}

object ExamplesBuild extends Build {
  import BuildSettings._
  import Dependencies._

  // configure prompt to show current project
  override lazy val settings = super.settings :+ {
    shellPrompt := { s => Project.extract(s).currentProject.id + "> " }
  }

  // root project
  lazy val examples = Project("sp-root", file("."))
    .aggregate(scaloidPlayer)

  // sub-projects
  lazy val scaloidPlayer = Project("scaloid-player", file("scaloid-player"))
    .settings(basicSettings: _*)
    .settings(libraryDependencies ++= Seq(scaloid))
    .settings(
      AndroidProject.androidSettings ++
      TypedResources.settings ++
      AndroidManifestGenerator.settings ++
      AndroidMarketPublish.settings ++
      Seq(
        versionCode := androidVerCd,
        platformName in Android := "android-16",
        useProguard in Android := true,
        proguardOption in Android := androidProguard) ++
      Seq(
        keyalias in Android := "change-me"
      ): _*)

  // proguard android options string
  val androidProguard = """
    -target 6
    -keep class scala.collection.SeqLike { public protected *; }
  """

}
