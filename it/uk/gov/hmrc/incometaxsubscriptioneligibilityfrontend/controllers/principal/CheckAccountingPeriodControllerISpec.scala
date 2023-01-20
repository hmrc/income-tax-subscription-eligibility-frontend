/*
 * Copyright 2019 HM Revenue & Customs
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

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import play.api.data.FormError
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws.WSResponse
import play.api.test.Helpers._
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{Base => commonMessages, CheckAccountingPeriod => messages}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.forms.AccountingPeriodCheckForm
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.{No, Yes, YesNo}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.servicemocks.AuditStub.verifyAuditContains
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class CheckAccountingPeriodControllerISpec extends ComponentSpecBase with ViewSpec {

  "GET /eligibility/accounting-period-check" should {
    lazy val result = get("/accounting-period-check")
    lazy val doc: Document = Jsoup.parse(result.body)
    lazy val content: Element = doc.mainContent

    "return OK" in {
      result must have(
        httpStatus(OK)
      )
    }

    "have the correct template details" when {
      "there is no error" in new TemplateViewTest(
        document = doc,
        title = messages.heading,
        backLink = Some(routes.PropertyTradingStartAfterController.show.url)
      )

      "there is an error" in {
        val errorPage: Document = Jsoup.parse(submitAccountingPeriodCheck(None).body)
        new TemplateViewTest(
          document = errorPage,
          title = messages.heading,
          error = Some(FormError(AccountingPeriodCheckForm.fieldName, messages.error)),
          backLink = Some(routes.PropertyTradingStartAfterController.show.url)
        )
      }
    }

    "have a view with the correct heading" in {
      doc.getH1Element.text mustBe messages.heading
    }

    "have a view with the correct caption" in {
      doc.getElementsByClass("hmrc-caption").first().text() mustBe messages.caption
    }

    "have a view with one hint message" in {
      content.getElementsByClass("govuk-hint").text mustBe messages.hint
    }

    "have a view with the correct values displayed in the form" in {
      val form = content.select("form")
      val labels = content.select("form").select("label")

      form.isEmpty mustBe false

      val radios = content.select("input[type=radio]")

      radios.size() mustBe 2
      radios.get(0).attr("id") mustBe "yes-no"
      labels.get(0).text() mustBe commonMessages.yes

      radios.get(1).attr("id") mustBe "yes-no-2"
      labels.get(1).text() mustBe commonMessages.no

      val submitButton = content.select("#continue-button")

      submitButton must have(
        text(commonMessages.continue)
      )
    }

    "have the correct form" in {
      doc.getForm must have(
        method("POST"),
        action(routes.CheckAccountingPeriodController.submit.url)
      )
    }
  }

  class PostSetup(answer: Option[YesNo]) {
    val response: WSResponse = submitAccountingPeriodCheck(answer)
  }

  "POST /eligibility/accounting-period-check" should {

    "return SEE_OTHER when selecting Yes and send an Audit" in new PostSetup(Some(Yes)) {
      val expectedAuditContainsYes: JsValue = Json.parse(
        """{ "userType" : "individual", "eligible" : "true" , "answer" : "yes", "question": "standardAccountingPeriod" }""")
      verifyAuditContains(expectedAuditContainsYes)
      response must have(
        httpStatus(SEE_OTHER),
        redirectUri("/report-quarterly/income-and-expenses/sign-up/eligibility/sign-up-to-pilot"),
      )
    }

    "return SEE_OTHER when selecting No and send an Audit" in new PostSetup(Some(No)) {
      val expectedAuditContainsNo: JsValue = Json.parse(
        """{ "userType" : "individual", "eligible" : "false" , "answer" : "no", "question": "standardAccountingPeriod" }""")
      verifyAuditContains(expectedAuditContainsNo)
      response must have(
        httpStatus(SEE_OTHER),
        redirectUri("/report-quarterly/income-and-expenses/sign-up/eligibility/error/cannot-sign-up")
      )

    }

    "return BADREQUEST when no Answer is given" in new PostSetup(None) {
      response must have(
        httpStatus(BAD_REQUEST)
      )
    }

    "have the correct form" in new PostSetup(None) {
      lazy val doc: Document = Jsoup.parse(response.body)
      doc.getForm must have(
        method("POST"),
        action(routes.CheckAccountingPeriodController.submit.url)
      )
    }
  }
}

