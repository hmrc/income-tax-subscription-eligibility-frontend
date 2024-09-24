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
import uk.gov.hmrc.govukfrontend.views.Aliases.{Hint, Text}
import uk.gov.hmrc.govukfrontend.views.viewmodels.radios.RadioItem
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{CheckAccountingPeriod => messages}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.forms.AccountingPeriodForm
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.AccountingPeriod
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.AccountingPeriod.{FirstAprilToThirtyFirstMarch, OtherAccountingPeriod, SixthAprilToFifthApril}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.servicemocks.AuditStub.verifyAuditContains
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class CheckAccountingPeriodControllerISpec extends ComponentSpecBase with ViewSpec {

  "GET /eligibility/accounting-period-check" should {
    def result: WSResponse = get("/accounting-period-check")

    def doc: Document = Jsoup.parse(result.body)

    "return OK" in {
      result must have(
        httpStatus(OK)
      )
    }

    "have the correct template details" when {
      "there is no error" in {
        new TemplateViewTest(
          document = doc,
          title = messages.heading,
          backLink = Some(routes.SigningUpController.show.url)
        )
      }

      "there is an error" in {
        val errorPage: Document = Jsoup.parse(submitAccountingPeriodCheck(None).body)
        new TemplateViewTest(
          document = errorPage,
          title = messages.heading,
          error = Some(FormError(AccountingPeriodForm.fieldName, messages.error)),
          backLink = Some(routes.SigningUpController.show.url)
        )
      }
    }

    "have the correct heading and caption" in {
      doc.mainContent.getH1Element.text mustBe messages.heading
    }

    "have a first para" in {
      doc.mainContent.selectNth("p", 1).text mustBe messages.paraOne
    }

    "have a second para" in {
      doc.mainContent.selectNth("p", 2).text mustBe messages.paraTwo
    }

    "have a form" which {
      def form: Element = doc.mainContent.getForm

      "has the correct attributes" in {
        form must have(
          method("POST"),
          action(routes.CheckAccountingPeriodController.submit.url)
        )
      }

      "has the correct radio inputs" when {
        val radioItems: Seq[RadioItem] = Seq(
          RadioItem(
            content = Text(messages.sixthToFifth),
            value = Some(AccountingPeriod.SixthAprilToFifthApril.key),
            hint = Some(Hint(content = Text(messages.sixthToFifthHint))),
          ),
          RadioItem(
            content = Text(messages.firstToThirtyFirst),
            value = Some(AccountingPeriod.FirstAprilToThirtyFirstMarch.key),
            hint = Some(Hint(content = Text(messages.firstToThirtyFirstHint))),
          ),
          RadioItem(
            divider = Some(messages.or)
          ),
          RadioItem(
            content = Text(messages.neither),
            value = Some(AccountingPeriod.OtherAccountingPeriod.key),
          )
        )
        "there is no error" in {
          doc.mustHaveRadioInput(
            selector = "fieldset"
          )(
            name = AccountingPeriodForm.fieldName,
            legend = messages.heading,
            isHeading = false,
            isLegendHidden = true,
            hint = None,
            errorMessage = None,
            radioContents = radioItems
          )
        }
        "there is an error" in {
          val errorPage: Document = Jsoup.parse(submitAccountingPeriodCheck(None).body)
          errorPage.mustHaveRadioInput(
            selector = "fieldset"
          )(
            name = AccountingPeriodForm.fieldName,
            legend = messages.heading,
            isHeading = false,
            isLegendHidden = true,
            hint = None,
            errorMessage = Some(messages.error),
            radioContents = radioItems
          )
        }
      }

      "has a continue button" in {
        form.selectHead(".govuk-button").text mustBe messages.continue
      }
    }
  }

  class PostSetup(answer: Option[AccountingPeriod]) {
    val response: WSResponse = submitAccountingPeriodCheck(answer)
  }

  "POST /eligibility/accounting-period-check" should {

    "return SEE_OTHER when selecting neither and send an Audit" in new PostSetup(Some(OtherAccountingPeriod)) {
      val expectedAuditContains: JsValue = Json.parse(
        """{ "userType" : "individual", "eligible" : "false" , "answer" : "other", "question": "standardAccountingPeriod" }""")

      response must have(
        httpStatus(SEE_OTHER),
        redirectUri("/report-quarterly/income-and-expenses/sign-up/eligibility/error/cannot-sign-up"),
      )

      verifyAuditContains(expectedAuditContains)
    }

    "return SEE_OTHER when selecting 6 April to 5 April and send an Audit" in new PostSetup(Some(SixthAprilToFifthApril)) {
      val expectedAuditContains: JsValue = Json.parse(
        """{ "userType" : "individual", "eligible" : "true" , "answer" : "6 april to 5 april", "question": "standardAccountingPeriod" }""")

      response must have(
        httpStatus(SEE_OTHER),
        redirectUri("/report-quarterly/income-and-expenses/sign-up/eligibility/sign-up-to-pilot"),
      )

      verifyAuditContains(expectedAuditContains)
    }

    "return SEE_OTHER when selecting 1 April to 31 March and send an Audit" in new PostSetup(Some(FirstAprilToThirtyFirstMarch)) {
      val expectedAuditContains: JsValue = Json.parse(
        """{ "userType" : "individual", "eligible" : "true" , "answer" : "1 april to 31 march", "question": "standardAccountingPeriod" }""")

      response must have(
        httpStatus(SEE_OTHER),
        redirectUri("/report-quarterly/income-and-expenses/sign-up/eligibility/sign-up-to-pilot"),
      )

      verifyAuditContains(expectedAuditContains)
    }

    "return BADREQUEST when no Answer is given" in new PostSetup(None) {
      response must have(
        httpStatus(BAD_REQUEST)
      )

      val doc: Document = Jsoup.parse(response.body)

      doc.getForm must have(
        method("POST"),
        action(routes.CheckAccountingPeriodController.submit.url)
      )
    }
  }
}

