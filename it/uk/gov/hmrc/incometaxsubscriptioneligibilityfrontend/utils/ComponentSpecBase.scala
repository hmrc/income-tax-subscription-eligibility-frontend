
package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.ws.{WSClient, WSRequest, WSResponse}
import play.api.test.Helpers._
import play.api.{Application, Environment, Mode}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.AppConfig

trait ComponentSpecBase extends PlaySpec with CustomMatchers with GuiceOneServerPerSuite {

  override lazy val app: Application = new GuiceApplicationBuilder()
    .in(Environment.simple(mode = Mode.Dev))
    .configure(config)
    .build

  implicit val appConfig = app.injector.instanceOf[AppConfig]

  implicit val ws: WSClient = app.injector.instanceOf[WSClient]

  def config: Map[String, String] = Map(
    "auditing.enabled" -> "false",
    "play.filters.csrf.header.bypassHeaders.Csrf-Token" -> "nocheck"
  )

  val baseUrl: String = "/report-quarterly/income-and-expenses/sign-up/eligibility"

  def buildClient(path: String): WSRequest =
    ws.url(s"http://localhost:$port$baseUrl$path").withFollowRedirects(false)

  def get(uri: String): WSResponse =
    await(
      buildClient(uri)
        .withHttpHeaders()
        .get()
    )

  def post(uri: String)(form: (String, String)*): WSResponse = {
    val formBody = (form map { case (k, v) => (k, Seq(v)) }).toMap
    await(
      buildClient(uri)
        .withHttpHeaders("Csrf-Token" -> "nocheck")
        .post(formBody)
    )
  }

}
