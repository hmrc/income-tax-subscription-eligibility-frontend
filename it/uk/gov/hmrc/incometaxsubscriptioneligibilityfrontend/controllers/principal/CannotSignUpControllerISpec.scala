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
    lazy val content: Element = doc.body()

    "return OK" in {
      result must have(
        httpStatus(OK)
      )
    }

    "have the correct template details" in new TemplateViewTest(doc, messages.heading, backLink = Some("javascript:history.back()"))(appConfig)

    "have a view with the correct heading" in {
      doc.getH1Element.text mustBe messages.heading
    }

    "have a view with the correct first paragraph" in {
      doc.getParagraphs.text().contains(messages.incomePara) mustBe true
    }

    "have a view with the correct income bullet points" in {
      val listItemOne: Element = content.selectFirst("ul.govuk-list--bullet").selectFirst("li:nth-of-type(1)")
      val listItemTwo: Element = content.selectFirst("ul.govuk-list--bullet").selectFirst("li:nth-of-type(2)")
      val listItemThree: Element = content.selectFirst("ul.govuk-list--bullet").selectFirst("li:nth-of-type(3)")
      val listItemFour: Element = content.selectFirst("ul.govuk-list--bullet").selectFirst("li:nth-of-type(4)")
      val listItemFive: Element = content.selectFirst("ul.govuk-list--bullet").selectFirst("li:nth-of-type(5)")

      listItemOne.text mustBe messages.incomeBullet1
      listItemTwo.text mustBe messages.incomeBullet2
      listItemThree.text mustBe messages.incomeBullet3
      listItemFour.text mustBe messages.incomeBullet4
      listItemFive.text mustBe messages.incomeBullet5
    }

    "have a view with the correct second paragraph" in {
      doc.getParagraphs.text().contains(messages.otherPara) mustBe true
    }

    "have a view with the correct other bullet points" in {
      content.select("ul:nth-of-type(2)").select("li:nth-of-type(1)").text mustBe messages.otherBullet1
      content.select("ul:nth-of-type(2)").select("li:nth-of-type(2)").text mustBe messages.otherBullet2
      content.select("ul:nth-of-type(2)").select("li:nth-of-type(3)").text mustBe messages.otherBullet3
    }

    "have a view with the correct Send Self Assessment Paragraph with link" in {
      val paragraph = content.select("p:nth-of-type(3)")
      paragraph.text mustBe messages.sendSelfAssessment

      val link = paragraph.select("a")
      link.text mustBe messages.sendSelfAssessmentLink
      link.attr("href") mustBe messages.sendSelfAssessmentHref
    }
  }

}
