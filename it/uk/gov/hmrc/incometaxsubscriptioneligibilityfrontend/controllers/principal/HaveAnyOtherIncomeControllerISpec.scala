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
import org.jsoup.nodes.Document
import play.api.data.FormError
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws.WSResponse
import play.api.test.Helpers._
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{Base => commonMessages, HaveAnyOtherIncome => messages}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.featureswitch.{FeatureSwitching, RemoveCovidPages}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.forms.HaveAnyOtherIncomeForm
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.{No, Yes, YesNo}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.servicemocks.AuditStub.{verifyAudit, verifyAuditContains}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class HaveAnyOtherIncomeControllerISpec extends ComponentSpecBase with ViewSpec with FeatureSwitching {

  override def beforeEach(): Unit = {
    disable(RemoveCovidPages)
    super.beforeEach()
  }

  "GET /eligibility/other-income" should {
    def result = get("/other-income")

    def doc: Document = Jsoup.parse(result.body)

    "return OK" in {
      result must have(
        httpStatus(OK)
      )
    }

    "have the correct template details" when {
      "there is no error and removeCovid feature switch is disabled" in {

        new TemplateViewTest(doc, messages.title, backLink = Some(routes.Covid19ClaimCheckController.show().url))
      }

      "there is no error and removeCovid feature switch is enabled" in {
        enable(RemoveCovidPages)
        new TemplateViewTest(doc, messages.title, backLink = Some(routes.OverviewController.show().url))
      }

      "there is an error" in {
        val errorPage: Document = Jsoup.parse(post("/other-income")(Map.empty).body)
        new TemplateViewTest(errorPage, messages.title, error = Some(FormError(HaveAnyOtherIncomeForm.fieldName, messages.error)))
      }
    }


    "have a view with the correct heading" in {
      doc.getH1Element.text mustBe messages.title
    }


    "have a view with two correct paragraph lines" in {
      doc.getParagraphs.text().contains(messages.include) mustBe true
      doc.getParagraphs.text().contains(messages.notInclude) mustBe true
    }

    "have a view with the correct bullet point list" in {
      doc.getBulletPoints.text().contains((messages.includePoints ++ messages.notIncludePoints).mkString(" ")) mustBe true
    }

    "have a view with the correct values displayed in the form" in {
      val form = doc.select("form")
      val labels = doc.select("form").select("label")

      form.isEmpty() mustBe false

      val radios = form.select("input[type=radio]")

      radios.size() mustBe 2
      radios.get(0).attr("id") mustBe "yes-no"
      labels.get(0).text() mustBe commonMessages.yes

      radios.get(1).attr("id") mustBe "yes-no-2"
      labels.get(1).text() mustBe commonMessages.no

      val submitButton = form.select("button[id=continue-button]")

      submitButton must have(
        text(commonMessages.continue)
      )
    }

    "have the correct form" in {
      doc.getForm must have(
        method("POST"),
        action(routes.HaveAnyOtherIncomeController.submit().url)
      )
    }
  }


  class PostSetup(answer: Option[YesNo]) {
    val response: WSResponse = submitHaveAnyOtherIncome(answer)
  }

  "POST /eligibility/other-income" should {

    "return SEE_OTHER when selecting Yes and send an Audit" in new PostSetup(Some(Yes)) {
      val expectedAuditContainsYes: JsValue = Json.parse(
        """{ "userType" : "individual", "eligible" : "false" , "answer" : "yes", "question": "otherIncomeSource" }""")
      verifyAudit()
      verifyAuditContains(expectedAuditContainsYes)
      response must have(
        httpStatus(SEE_OTHER),
        redirectUri(routes.CannotSignUpController.show().url)

      )
    }

    "return SEE_OTHER when selecting No and send an Audit" in new PostSetup(Some(No)) {
      val expectedAuditContainsNo: JsValue = Json.parse(
        """{ "userType" : "individual", "eligible" : "true" , "answer" : "no", "question": "otherIncomeSource" }""")
      verifyAudit()
      verifyAuditContains(expectedAuditContainsNo)
      response must have(
        httpStatus(SEE_OTHER),
        redirectUri(routes.SoleTraderStartAfterController.show().url)

      )

    }

    "return BADREQUEST when no Answer is given" in new PostSetup(None) {
      val doc: Document = Jsoup.parse(response.body)

      response must have(
        httpStatus(BAD_REQUEST)
      )
    }

    "have the correct form" in new PostSetup(None) {
      lazy val doc: Document = Jsoup.parse(response.body)
      doc.getForm must have(
        method("POST"),
        action(routes.HaveAnyOtherIncomeController.submit().url)
      )
    }
  }

}
