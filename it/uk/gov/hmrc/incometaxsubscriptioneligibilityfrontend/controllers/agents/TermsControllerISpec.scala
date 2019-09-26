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

package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers.agents

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import play.api.libs.ws.WSResponse
import play.api.test.Helpers._
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{AgentTerms => messages, Base => commonMessages}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class TermsControllerISpec extends ComponentSpecBase with ViewSpec {

  lazy val result: WSResponse = get("/client/terms-of-participation")
  lazy val doc: Document = Jsoup.parse(result.body)

  "GET /client/terms-of-participation" should {
    "return OK" in {
      result must have(
        httpStatus(OK)
      )
    }

    "return a view with a title" in {
      doc.title mustBe messages.title
    }

    "return a view with a heading" in {
      doc.getH1Element.text mustBe messages.heading
    }

    "return a view with two paragraph lines" in {
      doc.getParagraphs must have(
        elementWithValue(messages.line1),
        elementWithValue(messages.line2)
      )
    }

    "return a view with a bullet point list" in {
      doc.getBulletPoints must have(
        elementWithValue(messages.bullet1),
        elementWithValue(messages.bullet2),
        elementWithValue(messages.bullet3),
        elementWithValue(messages.bullet4),
        elementWithValue(messages.bullet5),
        elementWithValue(messages.bullet6)
      )
    }

    "return a view with an accept and continue button" in {
      doc.getSubmitButton.text mustBe commonMessages.acceptAndContinue
    }

    "have the correct form" in {
      doc.getForm must have(
        method("POST"),
        action(routes.TermsController.show().url) // TODO redirect to next page in agent flow
      )
    }
  }

  "POST /client/terms-of-participation" should {
    "Redirect to the 'Other Income' page in Income Tax Subscription" in {
      val result = post("/client/terms-of-participation")()
      result must have(
        httpStatus(SEE_OTHER),
        redirectUri("/report-quarterly/income-and-expenses/sign-up/client/client-details")
      )
    }
  }
}
