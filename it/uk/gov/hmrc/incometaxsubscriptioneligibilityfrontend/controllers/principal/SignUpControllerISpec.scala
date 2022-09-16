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
import play.api.test.Helpers.OK
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{Base => commonMessages, SignUpToPilot => messages}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class SignUpControllerISpec extends ComponentSpecBase with ViewSpec {
  "GET /eligibility/sign-up-to-pilot" should {
    lazy val result = get("/sign-up-to-pilot")
    lazy val doc: Document = Jsoup.parse(result.body)
    lazy val content: Element = doc.mainContent

    "return OK" in {
      result must have(
        httpStatus(OK)
      )
    }

    "have a view with the correct heading" in {
      content.getH1Element.text mustBe messages.heading
    }

    "have a view with the correct paragraph" in {
      content.getParagraphs.text mustBe messages.paragraph
    }

    "have a view with the correct continue button" in {
      content.getElementsByClass("govuk-button").text mustBe commonMessages.continue
      content.getElementsByClass("govuk-button").attr("href") mustBe routes.TermsController.show.url
    }

    "have a view with the correct back link" in {
      doc.getElementsByClass("govuk-back-link").text mustBe commonMessages.backUrl
      doc.getElementsByClass("govuk-back-link").attr("href") mustBe routes.CheckAccountingPeriodController.show.url
    }
  }
}
