
import play.core.PlayVersion.current
import sbt._

object AppDependencies {

  private val bootstrapVersion = "7.7.0"
  private val playHmrcFrontendVersion = "3.31.0-play-28"
  private val jacksonModuleVersion     = "2.13.2"
  private val playTest = "2.8.14"

  val compile = Seq(
    "uk.gov.hmrc" %% "bootstrap-frontend-play-28" % bootstrapVersion,
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
    "com.github.tomakehurst" % "wiremock-jre8" % "2.32.0" % "test, it",
    "org.mockito" % "mockito-core" % "3.12.4"% "test",
    "org.scalatestplus"            %% "mockito-3-12"         % "3.2.10.0" % "test"
  )

}
