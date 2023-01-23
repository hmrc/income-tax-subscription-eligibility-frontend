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
      doc.getH1Element.text mustBe messages.heading
    }

    "return a view with 1 paragraph lines" in {
      doc.select("div[class=govuk-form-group]").select("p:nth-of-type(1)").select("p") must have(
        elementWithValue(messages.line1)
      )
    }

    "return a view with a bullet point list" in {
      doc.select("div[class=govuk-form-group]").select("ul:nth-of-type(1)").select("li") must have(
        elementWithValue(messages.bullet1),
        elementWithValue(messages.bullet2),
        elementWithValue(messages.bullet3),
        elementWithValue(messages.bullet4)
      )
    }

    "return a view with the second paragraph line" in {
      doc.select("div[class=govuk-form-group]").select("p:nth-of-type(2)").select("p") must have(
        elementWithValue(messages.line2),
      )
    }

    "return a view with the third paragraph line" in {
      doc.select("div[class=govuk-form-group]").select("p:nth-of-type(3)").select("p") must have(
        elementWithValue(messages.paragraph1),
      )
    }

    "return a view with a second bullet point list" in {
      doc.select("div[class=govuk-form-group]").select("ul:nth-of-type(2)").select("li") must have(
        elementWithValue(messages.bullet5),
        elementWithValue(messages.bullet6)
      )
    }

    "return a view with the fourth paragraph line" in {
      doc.select("div[class=govuk-form-group]").select("p:nth-of-type(4)").select("p") must have(
        elementWithValue(messages.line3),
      )
    }

    "return a view with an accept and continue button" in {
      doc.select("button").last().text mustBe commonMessages.acceptAndContinue
    }

    "have the correct form" in {
      doc.getForm must have(
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
