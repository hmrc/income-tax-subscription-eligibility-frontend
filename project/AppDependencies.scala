import play.core.PlayVersion.current
import sbt._


object AppDependencies {

  private val playLanguageVersion = "4.3.0-play-26"

  val compile = Seq(
    "uk.gov.hmrc" %% "govuk-template" % "5.38.0-play-26",
    "uk.gov.hmrc" %% "play-ui" % "8.12.0-play-26",
    "uk.gov.hmrc" %% "bootstrap-play-26" % "0.45.0",
    "uk.gov.hmrc" %% "play-language" % playLanguageVersion
  )

  val test = Seq(
    "uk.gov.hmrc" %% "bootstrap-play-26" % "0.45.0" % Test classifier "tests",
    "org.scalatest" %% "scalatest" % "3.0.8" % "test",
    "org.jsoup" % "jsoup" % "1.10.2" % "test, it",
    "com.typesafe.play" %% "play-test" % current % "test",
    "org.pegdown" % "pegdown" % "1.6.0" % "test, it",
    "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % "test, it"
  )

}
