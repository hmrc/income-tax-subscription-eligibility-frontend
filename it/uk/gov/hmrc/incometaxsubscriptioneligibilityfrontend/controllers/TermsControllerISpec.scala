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

package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{Terms => messages}
import play.api.test.Helpers._

class TermsControllerISpec extends ComponentSpecBase with ViewSpec with GuiceOneServerPerSuite {

  lazy val result = get("/terms-of-participation")
  lazy val doc: Document = Jsoup.parse(result.body)

  "GET /terms-of-participation" should {
    "return OK" in {
      result must have(
        httpStatus(OK)
      )
    }

    "have a view with an Accept and continue button" in {
      doc.getSubmitButton.text mustBe messages.button
    }

    "have a view with a title" in {
      doc.title mustBe messages.title
    }

    "have a view with a heading" in {
      doc.getH1Element.text mustBe messages.heading
    }

    "have a view with two paragraph lines" in {
      doc.getParagraphs must have(
        elementWithValue(messages.line1),
        elementWithValue(messages.line2)
      )
    }

    "have a view with a bullet point list" in {
      doc.getBulletPoints must have(
        elementWithValue(messages.bullet1),
        elementWithValue(messages.bullet2),
        elementWithValue(messages.bullet3),
        elementWithValue(messages.bullet4),
        elementWithValue(messages.bullet5),
        elementWithValue(messages.bullet6),
        elementWithValue(messages.bullet7)
      )
    }
  }
}
