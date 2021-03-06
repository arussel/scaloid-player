resolvers ++= Seq(
  Resolver.url("scalasbt releases", new URL("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns),
  Resolver.url("scalasbt snapshots", new URL("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-snapshots"))(Resolver.ivyStylePatterns)
)

addSbtPlugin("org.scala-sbt" % "sbt-android-plugin" % "0.6.2")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.4.0")
