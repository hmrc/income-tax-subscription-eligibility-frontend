/*
 * Copyright 2020 HM Revenue & Customs
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

package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers.principal

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws.WSResponse
import play.api.test.Helpers.{BAD_REQUEST, OK, SEE_OTHER}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{suffix, Base => commonMessages, PropertyStartAfter => messages}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.{No, Yes, YesNo}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.servicemocks.AuditStub.{verifyAudit, verifyAuditContains}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class PropertyTradingStartAfterControllerISpec extends ComponentSpecBase with ViewSpec {

  val date: String = LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("d MMMM y"))

  "GET /eligibility/property-start-after" should {

    lazy val result = get("/property-start-after")
    lazy val doc: Document = Jsoup.parse(result.body)
    lazy val content: Element = doc.body()
    "return OK" in {
      result must have(
        httpStatus(OK)
      )
    }

    "have a view with the correct title" in {
      doc.title mustBe s"${messages.title(date)}$suffix"
    }

    "have a view with the correct heading" in {
      lazy val text1 = doc.getH1Element.text
      text1 mustBe messages.title(date)
    }

    "have a view with the correct hint paragraph" in {
      content.select("p:nth-of-type(1)").text must endWith(messages.hintMessage)
    }

    "have a view with the correct values displayed in the form" in {
      val form = doc.select("form")
      val labels = doc.select("form").select("label")
      val radios = form.select("input[type=radio]")

      radios.size() mustBe 2
      radios.get(0).attr("id") mustBe "yes-no"
      labels.get(0).text() mustBe commonMessages.yes

      radios.get(1).attr("id") mustBe "yes-no-2"
      labels.get(1).text() mustBe commonMessages.no

      val submitButton = form.select("button")

      submitButton must have(
        text(commonMessages.continue)
      )
    }

    "have the correct form" in {
      doc.getForm must have(
        method("POST"),
        action(routes.PropertyTradingStartAfterController.submit.url)
      )
    }
  }

  class PostSetup(answer: Option[YesNo]) {
    val response: WSResponse = submitPropertyTradingStartAfter(answer)
  }

  "POST /eligibility/other-income" should {

    "return SEE_OTHER when selecting yes and sent an Audit" in new PostSetup(Some(Yes)) {
      val expectedAuditContainsYes: JsValue = Json.parse(
        """{ "userType" : "individual", "eligible" : "false" , "answer" : "yes", "question": "propertyBusinessStartDate" }""")
      verifyAudit()
      verifyAuditContains(expectedAuditContainsYes)
      response must have(
        httpStatus(SEE_OTHER),
        redirectUri(routes.CannotSignUpController.show.url)
      )
    }

    "return SEE_OTHER when selecting No and send an Audit" in new PostSetup(Some(No)) {
      val expectedAuditContainsNo: JsValue = Json.parse(
        """{ "userType" : "individual", "eligible" : "true" , "answer" : "no", "question": "propertyBusinessStartDate" }""")
      verifyAudit()
      verifyAuditContains(expectedAuditContainsNo)
      response must have(
        httpStatus(SEE_OTHER),
        redirectUri(routes.CheckAccountingPeriodController.show.url)
      )

    }

    "return BADREQUEST when no Answer is given" in new PostSetup(None) {
      val doc: Document = Jsoup.parse(response.body)

      response must have(
        httpStatus(BAD_REQUEST)
      )

      val errorMessage = doc.select("div[class=govuk-error-summary__body]")
      errorMessage.text() mustBe messages.error(date)

      doc.getForm must have(
        method("POST"),
        action(routes.PropertyTradingStartAfterController.submit.url)
      )
    }

  }

}
