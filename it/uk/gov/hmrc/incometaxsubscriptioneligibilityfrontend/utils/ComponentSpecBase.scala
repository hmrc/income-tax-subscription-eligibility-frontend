
package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils

import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.ws.WSResponse
import play.api.test.Helpers._
import play.api.{Application, Environment, Mode}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.AppConfig
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.forms.{AccountingPeriodCheckForm, HaveAnyOtherIncomeForm, PropertyTradingStartDateForm}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.YesNo
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.servicemocks.{AuditStub, WireMockMethods}

trait ComponentSpecBase extends PlaySpec with CustomMatchers with GuiceOneServerPerSuite with WireMockMethods
  with BeforeAndAfterEach with BeforeAndAfterAll with WiremockHelper {

  override implicit lazy val app: Application = new GuiceApplicationBuilder()
    .in(Environment.simple(mode = Mode.Dev))
    .configure(config)
    .build

  implicit val appConfig = app.injector.instanceOf[AppConfig]

  lazy val mockHost: String = WiremockHelper.wiremockHost
  lazy val mockPort: String = WiremockHelper.wiremockPort.toString

  def config: Map[String, String] = Map(
    "auditing.enabled" -> "true",
    "play.filters.csrf.header.bypassHeaders.Csrf-Token" -> "nocheck",
    "auditing.consumer.baseUri.host" -> mockHost,
    "auditing.consumer.baseUri.port" -> mockPort
  )

  override def beforeEach(): Unit = {
    super.beforeEach()
    resetWiremock()
    AuditStub.stubAuditing()
  }

  override def beforeAll(): Unit = {
    super.beforeAll()
    startWiremock()
  }

  override def afterAll(): Unit = {
    stopWiremock()
    super.afterAll()
  }

  def get(uri: String): WSResponse =
    await(
      buildClient(uri)
        .withHttpHeaders()
        .get()
    )

  def getWithHeaders(uri: String, headers: (String, String)*): WSResponse = {
    await(
      buildClient(uri)
        .withHttpHeaders(headers: _*)
        .get()
    )
  }

  def post(uri: String)(form: Map[String, Seq[String]] = Map.empty): WSResponse = {
    await(
      buildClient(uri)
        .withHttpHeaders("Csrf-Token" -> "nocheck")
        .post(form)
    )
  }

  def submitAccountingPeriodCheck(request: Option[YesNo]): WSResponse = {
    post("/accounting-period-check")(
      request.fold(Map.empty[String, Seq[String]])(
        model => AccountingPeriodCheckForm.accountingPeriodCheckForm.fill(model).data.map { case (k, v) => (k, Seq(v)) }
      )
    )
  }

  def submitHaveAnyOtherIncome(request: Option[YesNo]): WSResponse = {
    post("/other-income")(
      request.fold(Map.empty[String, Seq[String]])(
        model => HaveAnyOtherIncomeForm.haveAnyOtherIncomeForm.fill(model).data.map { case (k, v) => (k, Seq(v)) }
      )
    )
  }

  def submitPropertyTradingStartAfter(request: Option[YesNo]): WSResponse = {
    post("/property-start-after")(
      request.fold(Map.empty[String, Seq[String]])(
        model => PropertyTradingStartDateForm.propertyTradingStartDateForm("").fill(model).data.map { case (k, v) => (k, Seq(v)) }
      )
    )
  }

  def submitSoleTraderStartAfter(request: Option[YesNo]): WSResponse = {
    post("/sole-trader-start-after")(
      request.fold(Map.empty[String, Seq[String]])(
        model => PropertyTradingStartDateForm.propertyTradingStartDateForm("").fill(model).data.map { case (k, v) => (k, Seq(v)) }
      )
    )
  }

}
