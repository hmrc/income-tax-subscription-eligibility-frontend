/*
 * Copyright 2023 HM Revenue & Customs
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
import play.api.test.Helpers._
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{Base => base, Overview => messages}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class OverviewControllerISpec extends ComponentSpecBase with ViewSpec {


  "GET /overview" should {

    lazy val result = get("/overview")
    lazy val doc: Document = Jsoup.parse(result.body)
    lazy val content: Element = doc.mainContent

    "return OK" in {
      result must have(
        httpStatus(OK)
      )
    }

    "have the correct template details" in new TemplateViewTest(doc, messages.heading)(appConfig)

    "return a view with a heading" in {
      content.getH1Element.text mustBe messages.heading
    }

    "return a view with the first paragraph" in {
      content.select("p:nth-of-type(1)").text mustBe messages.line1
    }

    "return a view with the second paragraph" in {
      content.select("p:nth-of-type(2)").text mustBe messages.line2
    }

    "return a view with 3 bullet points" in {
      content.select("ul:nth-of-type(1)").select("li:nth-of-type(1)").text mustBe messages.bulletCompatibleSoftware1
      content.select("ul:nth-of-type(1)").select("li:nth-of-type(2)").text mustBe messages.bulletCompatibleSoftware2
      content.select("ul:nth-of-type(1)").select("li:nth-of-type(3)").text mustBe messages.bulletCompatibleSoftware3
    }

    "return a view with the third paragraph with a link" in {
      content.select("p:nth-of-type(3)").text mustBe messages.line3
    }

    "return a view with the correct second heading" in {
      content.getH2Elements.text mustBe messages.heading2
    }

    "return a view with the four paragraph" in {
      content.select("p:nth-of-type(4)").text mustBe messages.line4
    }

    "return a view with four bullet points" in {
      content.select("ul:nth-of-type(2)").select("li:nth-of-type(1)").text mustBe messages.bulletBeforeStart1
      content.select("ul:nth-of-type(2)").select("li:nth-of-type(2)").text mustBe messages.bulletBeforeStart2
      content.select("ul:nth-of-type(2)").select("li:nth-of-type(3)").text mustBe messages.bulletBeforeStart3
      content.select("ul:nth-of-type(2)").select("li:nth-of-type(4)").text mustBe messages.bulletBeforeStart4
    }

    "return a view with inset text" in {
      content.getElementsByClass("govuk-inset-text").text mustBe messages.insetText
    }

    "return a view with a continue button" in {
      doc.getSubmitButton.text mustBe base.continue
    }

    "have the correct form" in {
      doc.getForm must have(
        method("GET"),
        action(routes.CheckAccountingPeriodController.show.url)
      )
    }

  }

}
