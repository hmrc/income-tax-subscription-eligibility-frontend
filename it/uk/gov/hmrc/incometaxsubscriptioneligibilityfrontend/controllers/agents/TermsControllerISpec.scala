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
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{agentSuffix, AgentTerms => messages, Base => commonMessages}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.featureswitch.FeatureSwitch.SignUpEligibilityInterrupt
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.featureswitch.FeatureSwitching
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class TermsControllerISpec extends ComponentSpecBase with ViewSpec with FeatureSwitching {

  def result: WSResponse = get("/client/what-you-need-to-do")
  def doc: Document = Jsoup.parse(result.body)

  lazy val submitResult: WSResponse = post("/client/what-you-need-to-do")(Map.empty)

  override def beforeEach(): Unit = {
    super.beforeEach()
    disable(SignUpEligibilityInterrupt)
  }

  "GET /client/what-you-need-to-do" should {
    "return OK" in {
      result must have(
        httpStatus(OK)
      )
    }

    "uses the correct template details" when {
      "feature switch is enabled" in {
        enable(SignUpEligibilityInterrupt)
        new TemplateViewTest(
          document = doc,
          title = messages.heading,
          isAgent = true,
          backLink = Some(routes.SigningUpController.show.url)
        )(appConfig)
      }
      "feature switch is disabled" in {

        new TemplateViewTest(
          document = doc,
          title = messages.heading,
          isAgent = true,
          backLink = Some(appConfig.govukGuidanceITSASignUpAgentLink)
        )(appConfig)
      }
    }

    "return a view with a heading" in {
      doc.mainContent.getH1Element.text mustBe messages.heading
    }

    "return a view with paragraph 1" in {
      doc.mainContent.selectNth("p", 1).select("p").text mustBe messages.paragraph1
    }

    "return a view with a bullet point list" in {
      doc.mainContent.selectNth("ul", 1).selectNth("li", 1).text mustBe messages.bullet1
      doc.mainContent.selectNth("ul", 1).selectNth("li", 2).text mustBe messages.bullet2
      doc.mainContent.selectNth("ul", 1).selectNth("li", 3).text mustBe messages.bullet3
      doc.mainContent.selectNth("ul", 1).selectNth("li", 4).text mustBe messages.bullet4
    }

    "return a view with a subheading" in {
      doc.mainContent.selectNth("p", 2).text mustBe messages.subheading
    }

    "return a view with paragraph 2" in {
      doc.mainContent.selectNth("p", 3).text  mustBe messages.paragraph2
    }

    "return a view with paragraph 3" in {
      doc.mainContent.selectNth("p", 4).text mustBe messages.paragraph3
    }

    "return a view with a second bullet point list" in {
      doc.mainContent.selectNth("ul", 2).selectNth("li", 1).text mustBe messages.bullet5
      doc.mainContent.selectNth("ul", 2).selectNth("li", 2).text mustBe messages.bullet6
      doc.mainContent.selectNth("ul", 2).selectNth("li", 3).text mustBe messages.bullet7

    }

    "return a view with paragraph 4" in {
      doc.mainContent.selectNth("p", 5).text mustBe messages.paragraph4
    }

    "return a view with a third bullet point list" in {
      doc.mainContent.selectNth("ul", 3).selectNth("li", 1).text mustBe messages.bullet8
      doc.mainContent.selectNth("ul", 3).selectNth("li", 2).text mustBe messages.bullet9
      doc.mainContent.selectNth("ul", 3).selectNth("li", 3).text mustBe messages.bullet10
    }

    "return a view with paragraph 5" in {
      doc.mainContent.selectNth("p", 6).text mustBe messages.paragraph5
    }

    "return a view with an accept and continue button" in {
      doc.mainContent.select("button").last().text mustBe commonMessages.acceptAndContinue
    }

    "have the correct form" in {
      doc.mainContent.getForm must have(
        method("POST"),
        action(routes.TermsController.submit.url)
      )
    }
  }

  "POST /client/what-you-need-to-do" should {
    "redirect to the start of the agent sign up" in {
      submitResult must have(
        httpStatus(SEE_OTHER),
        redirectUri(appConfig.incometaxSubscriptionFrontendAgentIncomeSourcesPageFullUrl)
      )
    }
  }
}
