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
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws.WSResponse
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements
import play.api.test.Helpers._
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{suffix, Base => commonMessages, Covid19ClaimCheck => messages}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.{No, Yes, YesNo}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.servicemocks.AuditStub.{verifyAudit, verifyAuditContains}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class Covid19ClaimCheckControllerISpec extends ComponentSpecBase with ViewSpec {

  "GET /eligibility/covid-19" should {
    lazy val result = get("/covid-19")
    lazy val doc: Document = Jsoup.parse(result.body)
    lazy val content: Element = doc.content

    "return OK" in {
      result must have(
        httpStatus(OK)
      )
    }

    "have a view with the correct title" in {
      doc.title mustBe s"${messages.title}$suffix"
    }

    "have a view with the correct heading" in {
      doc.getH1Element.text mustBe messages.title
    }

    "have a view with two correct paragraph lines" in {
      doc.getParagraphs.text().contains(messages.join_pilot) mustBe true
      doc.getParagraphs.text().contains(messages.claim_sick_pay) mustBe true
    }

    "return a view with the 2 correct bullet point for cannot join pilot" in {
      val listItemOne: Element = content.selectFirst("ul:nth-of-type(1)").selectFirst("li:nth-of-type(1)")
      val listItemTwo: Element = content.selectFirst("ul:nth-of-type(1)").selectFirst("li:nth-of-type(2)")

      listItemOne.text mustBe messages.linkTextCannotJoin1
      listItemOne.selectFirst("a").attr("href") mustBe appConfig.covid19SelfEmploymentSupportSchemeUrl

      listItemTwo.text mustBe messages.linkTextCannotJoin2
      listItemTwo.selectFirst("a").attr("href") mustBe appConfig.covid19CoronaJobRetentionSchemeUrl
    }

    "return a view with the 3 correct bullet point for can join pilot" in {
      val listItemOne: Element = content.selectFirst("ul:nth-of-type(2)").selectFirst("li:nth-of-type(1)")
      val listItemTwo: Element = content.selectFirst("ul:nth-of-type(2)").selectFirst("li:nth-of-type(2)")
      val listItemThree: Element = content.selectFirst("ul:nth-of-type(2)").selectFirst("li:nth-of-type(3)")

      listItemOne.text mustBe messages.linkTextCanJoin1
      listItemOne.selectFirst("a").attr("href") mustBe appConfig.covid19ClaimSickPayUrl

      listItemTwo.text mustBe messages.linkTextCanJoin2
      listItemTwo.selectFirst("a").attr("href") mustBe appConfig.covid19TestAndTraceUrl

      listItemThree.text mustBe messages.linkTextCanJoin3
      listItemThree.selectFirst("a").attr("href") mustBe appConfig.covid19LocalAuthorityGrantUrl
    }

    "have a view with the correct values displayed in the form" in {
      val form = doc.select("form")
      val labels = doc.select("form").select("label")

      form.isEmpty mustBe false

      val radios = form.select("input[type=radio]")

      radios.size() mustBe 2
      radios.get(0).attr("id") mustBe "yes-no"
      labels.get(0).text() mustBe commonMessages.yes

      radios.get(1).attr("id") mustBe "yes-no-2"
      labels.get(1).text() mustBe commonMessages.no

      val submitButton = form.select("button[type=submit]")

      submitButton must have(
        text(commonMessages.continue)
      )
    }

    "have the correct form" in {
      doc.getForm must have(
        method("POST"),
        action(routes.Covid19ClaimCheckController.submit().url)
      )
    }
  }

  class PostSetup(answer: Option[YesNo]) {
    val response: WSResponse = submitCovid19ClaimCheck(answer)
  }

  "POST /eligibility/covid-19" should {

    "return SEE_OTHER when selecting yes and send an Audit" in new PostSetup(Some(Yes)) {
      val expectedAuditContainsYes: JsValue = Json.parse(
        """{ "userType" : "individual", "eligible" : "false" , "answer" : "yes", "question": "claimedCovidGrant" }""")
      verifyAudit()
      verifyAuditContains(expectedAuditContainsYes)
      response must have(
        httpStatus(SEE_OTHER),
        redirectUri(routes.CovidCannotSignupController.show().url)
      )
    }

    "return SEE_OTHER when selecting No and send an Audit" in new PostSetup(Some(No)) {
      val expectedAuditContainsNo: JsValue = Json.parse(
        """{ "userType" : "individual", "eligible" : "true" , "answer" : "no", "question": "claimedCovidGrant" }""")
      verifyAudit()
      verifyAuditContains(expectedAuditContainsNo)
      response must have(
        httpStatus(SEE_OTHER),
        redirectUri(routes.HaveAnyOtherIncomeController.show().url)
      )
    }

    "return BADREQUEST when no Answer is given" in new PostSetup(None) {
      val doc: Document = Jsoup.parse(response.body)

      response must have(
        httpStatus(BAD_REQUEST)
      )

      val errorMessage: Elements = doc.select("div[class=error-notification]")
      errorMessage.text() mustBe messages.error
    }

    "have the correct form" in new PostSetup(None) {
      lazy val doc: Document = Jsoup.parse(response.body)
      doc.getForm must have(
        method("POST"),
        action(routes.Covid19ClaimCheckController.submit().url)
      )
    }
  }

}
