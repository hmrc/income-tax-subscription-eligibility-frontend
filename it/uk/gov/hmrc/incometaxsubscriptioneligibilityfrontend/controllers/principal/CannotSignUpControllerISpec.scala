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
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{suffix, CannotSignUp => messages}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class CannotSignUpControllerISpec extends ComponentSpecBase with ViewSpec {
  "GET /error/cannot-sign-up" should {
    lazy val result = get("/error/cannot-sign-up")
    lazy val doc: Document = Jsoup.parse(result.body)
    lazy val content: Element = doc.content

    "return OK" in {
      result must have(
        httpStatus(OK)
      )
    }

    "have a view with the correct title" in {
      doc.title mustBe s"${messages.title}${suffix}"
    }

    "have a view with the correct heading" in {
      content.getH1Element.text mustBe messages.title
    }

    "have a view with the correct first paragraph" in {
      content.select("p:nth-of-type(1)").text mustBe messages.incomePara
    }

    "have a view with the correct income bullet points" in {
      content.select("ul:nth-of-type(1)").select("li:nth-of-type(1)").text mustBe messages.incomeBullet1
      content.select("ul:nth-of-type(1)").select("li:nth-of-type(2)").text mustBe messages.incomeBullet2
      content.select("ul:nth-of-type(1)").select("li:nth-of-type(3)").text mustBe messages.incomeBullet3
      content.select("ul:nth-of-type(1)").select("li:nth-of-type(4)").text mustBe messages.incomeBullet4
      content.select("ul:nth-of-type(1)").select("li:nth-of-type(5)").text mustBe messages.incomeBullet5
    }

    "have a view with the correct second paragraph" in{
      content.select("p:nth-of-type(2)").text mustBe messages.otherPara
    }

    "have a view with the correct other bullet points" in {
      content.select("ul:nth-of-type(2)").select("li:nth-of-type(1)").text mustBe messages.otherBullet1
      content.select("ul:nth-of-type(2)").select("li:nth-of-type(2)").text mustBe messages.otherBullet2
      content.select("ul:nth-of-type(2)").select("li:nth-of-type(3)").text mustBe messages.otherBullet3
      content.select("ul:nth-of-type(2)").select("li:nth-of-type(4)").text mustBe messages.otherBullet4
    }

    "have a view with the correct Send Assessment Paragraph with link" in {
      content.select("p:nth-of-type(3)").text mustBe messages.sendSelfAssessment
      content.select("a").text mustBe messages.sendSelfAssessmentLink
      content.select("a").attr("href") mustBe messages.sendSelfAssessmentHref
    }
  }
}
