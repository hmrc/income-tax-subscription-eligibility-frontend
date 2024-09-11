import play.sbt.PlayImport.PlayKeys.playDefaultPort
import uk.gov.hmrc.DefaultBuildSettings

val appName = "income-tax-subscription-eligibility-frontend"

lazy val scoverageSettings = {

  import scoverage.ScoverageKeys

  val exclusionsList = List(
    "<empty>",
    "Reverse.*",
    "uk.gov.hmrc.BuildInfo",
    "app.*",
    "prod.*",
    "core.config.*",
    "com.*",
    "testonly.*",
    "business.*",
    "testOnlyDoNotUseInAppConf.*",
    "uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.testonly.*",
    "uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.views.html.*",
    "uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers.*Reverse*.*"
  )

  Seq(
    // Semicolon-separated list of regexs matching classes to exclude
    ScoverageKeys.coverageExcludedPackages := exclusionsList.mkString(";"),
    ScoverageKeys.coverageMinimumStmtTotal := 90,
    ScoverageKeys.coverageFailOnMinimum := false,
    ScoverageKeys.coverageHighlighting := true
  )
}

ThisBuild / majorVersion := 1
ThisBuild / scalaVersion := "2.13.13"

lazy val microservice = Project(appName, file("."))
  .enablePlugins(play.sbt.PlayScala, SbtDistributablesPlugin)
  .settings(
    libraryDependencies ++= AppDependencies.compile ++ AppDependencies.test,
    playDefaultPort := 9589,
    scalacOptions += "-Wconf:src=routes/.*:s",
    scalacOptions += "-Wconf:cat=unused-imports&src=html/.*:s",
    scoverageSettings,
  )

Test / Keys.fork := true
Test / javaOptions += "-Dlogger.resource=logback-test.xml"
Test / parallelExecution := true

lazy val it = project
  .enablePlugins(play.sbt.PlayScala)
  .dependsOn(microservice % "test->test") // the "test->test" allows reusing test code and test dependencies
  .settings(
    libraryDependencies ++= AppDependencies.itTest,
    DefaultBuildSettings.itSettings()
  )

TwirlKeys.templateImports ++= Seq(
  "uk.gov.hmrc.govukfrontend.views.html.components._",
  "uk.gov.hmrc.hmrcfrontend.views.html.components._",
  "uk.gov.hmrc.hmrcfrontend.views.html.helpers._",
  "uk.gov.hmrc.govukfrontend.views.html.components.implicits._"
)