
import sbt._

object AppDependencies {

  private val bootstrapVersion = "7.20.0"
  private val playHmrcFrontendVersion = "8.1.0"
  private val jacksonModuleVersion = "2.13.2"
  private val playTest = "2.8.14"

  val compile: Seq[ModuleID] = Seq(
    "uk.gov.hmrc" %% "bootstrap-frontend-play-28" % bootstrapVersion,
    "uk.gov.hmrc" %% "play-frontend-hmrc-play-28" % playHmrcFrontendVersion
  )

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc" %% "bootstrap-frontend-play-28" % bootstrapVersion % Test,
    "uk.gov.hmrc" %% "bootstrap-test-play-28" % bootstrapVersion % Test,
    "org.scalatest" %% "scalatest" % "3.2.16" % "test, it",
    "com.vladsch.flexmark" % "flexmark-all" % "0.64.6" % "test, it",
    "org.jsoup" % "jsoup" % "1.16.1" % "test, it",
    "com.typesafe.play" %% "play-test" % playTest % "test, it",
    "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % "test, it",
    "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonModuleVersion % "test, it",
    "com.github.tomakehurst" % "wiremock-jre8" % "2.35.0" % "test, it",
    "org.mockito" % "mockito-core" % "3.12.4" % "test",
    "org.scalatestplus" %% "mockito-3-12" % "3.2.10.0" % "test"
  )

}
