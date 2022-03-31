
import play.core.PlayVersion.current
import sbt._

object AppDependencies {

  private val playLanguageVersion = "5.2.0-play-28"
  private val bootstrapVersion = "5.21.0"
  private val playHmrcFrontendVersion = "3.9.0-play-28"
  private val jacksonModuleVersion     = "2.13.2"
  private val playTest = "2.8.14"

  val compile = Seq(
    "uk.gov.hmrc" %% "bootstrap-frontend-play-28" % bootstrapVersion,
    "uk.gov.hmrc" %% "play-language" % playLanguageVersion,
    "uk.gov.hmrc" %% "play-frontend-hmrc" % playHmrcFrontendVersion
  )

  val test = Seq(
    "uk.gov.hmrc" %% "bootstrap-frontend-play-28" % bootstrapVersion % Test,
    "org.scalatest" %% "scalatest" % "3.2.11" % "test, it",
    "com.vladsch.flexmark" % "flexmark-all" % "0.62.2" % "test, it",
    "org.jsoup" % "jsoup" % "1.14.3" % "test, it",
    "com.typesafe.play" %% "play-test" % playTest % "test, it",
    "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % "test, it",
    "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonModuleVersion % "test, it",
    "com.github.tomakehurst" % "wiremock-jre8" % "2.32.0" % "test, it"
  )

}
