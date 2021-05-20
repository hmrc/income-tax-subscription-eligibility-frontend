
import play.core.PlayVersion.current
import sbt._

object AppDependencies {

  private val playLanguageVersion = "4.10.0-play-26"
  private val bootstrapVersion = "2.3.0"
  private val playJsonJodaVersion = "2.9.2"
  private val playHmrcFrontendVersion = "0.65.0-play-26"
  private val govukFrontendVersion = "3.10.2"

  val compile = Seq(
    "uk.gov.hmrc" %% "govuk-template" % "5.60.0-play-26",
    "uk.gov.hmrc" %% "play-ui" % "8.19.0-play-26",
    "uk.gov.hmrc" %% "bootstrap-play-26" % bootstrapVersion,
    "uk.gov.hmrc" %% "play-language" % playLanguageVersion,
    "com.typesafe.play" %% "play-json-joda" % playJsonJodaVersion,
    "uk.gov.hmrc" %% "play-frontend-hmrc" % playHmrcFrontendVersion,
    "org.webjars.npm" % "govuk-frontend" % govukFrontendVersion
  )

  val test = Seq(
    "uk.gov.hmrc" %% "bootstrap-play-26" % bootstrapVersion % Test classifier "tests",
    "org.scalatest" %% "scalatest" % "3.0.9" % "test",
    "org.jsoup" % "jsoup" % "1.13.1" % "test, it",
    "com.typesafe.play" %% "play-test" % current % "test",
    "org.pegdown" % "pegdown" % "1.6.0" % "test, it",
    "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.3" % "test, it",
    "com.github.tomakehurst" % "wiremock-jre8" % "2.27.2" % "test, it"
  )

}
