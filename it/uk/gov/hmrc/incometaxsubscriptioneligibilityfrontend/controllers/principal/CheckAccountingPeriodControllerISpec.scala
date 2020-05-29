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
import play.api.test.Helpers._
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{suffix, Base => commonMessages, CheckAccountingPeriod => messages}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class CheckAccountingPeriodControllerISpec extends ComponentSpecBase with ViewSpec {

  "GET /eligibility/accounting-period-check" should {
    lazy val result = get("/accounting-period-check")
    lazy val doc: Document = Jsoup.parse(result.body)

    "return OK" in {
      result must have(
        httpStatus(OK)
      )
    }

    "have a view with the correct title" in {
      doc.title mustBe s"${messages.title}"
    }

    "have a view with the correct heading" in {
      doc.getH1Element.text mustBe messages.heading
    }

    "have a view with one hint" in {
      doc.getHintText.contains(messages.hint) mustBe true
    }

    "have a view with the correct values displayed in the form" in {
      val form = doc.select("form")
      val labels = doc.select("form").select("label")

      form.isEmpty() mustBe false

      val radios = form.select("input[type=radio]")

      radios.size() mustBe 2
      radios.get(0).attr("id") mustBe "choice-Yes"
      labels.get(0).text() mustBe commonMessages.yes

      radios.get(1).attr("id") mustBe "choice-No"
      labels.get(1).text() mustBe commonMessages.no

      val submitButton = form.select("button[type=submit]")

      submitButton must have(
        text(commonMessages.continue)
      )
    }

    "have the correct form" in {
      doc.getForm must have(
        method("POST"),
        action(routes.CheckAccountingPeriodController.submit().url)
      )
    }
  }

  "POST /eligibility/accounting-period-check" should {

    "return SEE_OTHER when selecting yes" in {
      val result = post("/accounting-period-check")("yes-no" -> "yes")

      result must have(
        httpStatus(SEE_OTHER),
        redirectUri("/report-quarterly/income-and-expenses/sign-up/eligibility/terms-of-participation")
      )
    }

    "return SEE_OTHER when selecting No" in {
      val result = post("/accounting-period-check")("yes-no" -> "no")

      result must have(
        httpStatus(SEE_OTHER),
        //This needs to be changed to CannotSignup page
        redirectUri("/report-quarterly/income-and-expenses/sign-up/eligibility/error/cannot-sign-up")
      )

    }

    "return BADREQUEST when no Answer is given" in {
      val result = post("/accounting-period-check")("yes-no" -> " ")
      val doc: Document = Jsoup.parse(result.body)

      result must have(
        httpStatus(BAD_REQUEST)
      )

      val errorMessage = doc.select("span[class=error-notification bold]")
      errorMessage.text() mustBe messages.error
    }

    "have the correct form" in {
      val result = post("/accounting-period-check")("yes-no" -> " ")
      lazy val doc: Document = Jsoup.parse(result.body)
      doc.getForm must have(
        method("POST"),
        action(routes.CheckAccountingPeriodController.submit().url)
      )
    }
  }
}

