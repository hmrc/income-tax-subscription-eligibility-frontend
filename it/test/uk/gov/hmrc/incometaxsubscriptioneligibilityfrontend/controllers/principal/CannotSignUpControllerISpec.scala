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
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{CannotSignUp => messages}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class CannotSignUpControllerISpec extends ComponentSpecBase with ViewSpec {

  "GET /error/cannot-sign-up" should {
    lazy val result = get("/error/cannot-sign-up")
    lazy val doc: Document = Jsoup.parse(result.body)
    lazy val content: Element = doc.mainContent

    "return OK" in {
      result must have(
        httpStatus(OK)
      )
    }

    "have the correct template details" in new TemplateViewTest(
      doc,
      messages.heading
    )(appConfig)

    "have a view with the correct heading" in {
      doc.getH1Element.text mustBe messages.heading
    }

    "have a view with the correct first paragraph" in {
      content.selectNth("p", 1).text mustBe messages.paraOne
    }

    "have a view with a bullet list" which {
      def bulletList: Element = content.selectHead("ul")

      "contains a first bullet point" in {
        bulletList.selectNth("li", 1).text mustBe messages.bulletOne
      }
      "contains a second bullet point" in {
        bulletList.selectNth("li", 2).text mustBe messages.bulletTwo
      }
    }

    "have a view with the correct second paragraph" in {
      content.selectNth("p", 2).text mustBe messages.paraTwo
    }

    "have a view with the correct third paragraph" in {
      content.selectNth("p", 3).text mustBe messages.paraThree
    }

    "have a view with the correct Send Self Assessment Paragraph with link" in {
      val paragraph = content.selectNth("p", 4)
      paragraph.text mustBe messages.sendSelfAssessment

      val link = paragraph.select("a")
      link.text mustBe messages.sendSelfAssessmentLink
      link.attr("href") mustBe messages.sendSelfAssessmentHref
    }
  }

}
