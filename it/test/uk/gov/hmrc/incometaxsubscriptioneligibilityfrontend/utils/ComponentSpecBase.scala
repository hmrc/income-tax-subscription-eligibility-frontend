/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils

import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.ws.WSResponse
import play.api.test.Helpers._
import play.api.{Application, Environment, Mode}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.AppConfig
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.forms.AccountingPeriodForm
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.AccountingPeriod
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.servicemocks.{AuditStub, WireMockMethods}

trait ComponentSpecBase extends PlaySpec with CustomMatchers with GuiceOneServerPerSuite with WireMockMethods
  with BeforeAndAfterEach with BeforeAndAfterAll with WiremockHelper {

  override implicit lazy val app: Application = new GuiceApplicationBuilder()
    .in(Environment.simple(mode = Mode.Dev))
    .configure(config)
    .build()

  implicit val appConfig: AppConfig = app.injector.instanceOf[AppConfig]

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

  def submitAccountingPeriodCheck(request: Option[AccountingPeriod]): WSResponse = {
    post("/accounting-period-check")(
      request.fold(Map.empty[String, Seq[String]])(
        model => AccountingPeriodForm.accountingPeriodForm.fill(model).data.map { case (k, v) => (k, Seq(v)) }
      )
    )
  }

}
