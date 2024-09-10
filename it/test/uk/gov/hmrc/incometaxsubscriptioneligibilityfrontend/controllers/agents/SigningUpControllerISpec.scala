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
import org.jsoup.nodes.{Document, Element}
import play.api.libs.ws.WSResponse
import play.api.test.Helpers._
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.AgentSignupTerms._
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.Base
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class SigningUpControllerISpec extends ComponentSpecBase with ViewSpec {

  private val path = "/client/signing-up"
  lazy val result: WSResponse = get(path)
  lazy val doc: Document = Jsoup.parse(result.body)

  lazy val submitResult: WSResponse = post(path)(Map.empty)

  s"GET $path" should {
    "return OK" in {
      result must have(
        httpStatus(OK)
      )
    }

    "return a view" which {
      "uses the correct template details" in new TemplateViewTest(
        document = doc,
        title = Heading.heading,
        isAgent = true,
        backLink = Some(appConfig.govukGuidanceITSASignUpAgentLink)
      )(appConfig)

      val mainContent = doc.getElementById("main-content")

      "has main content" which {
        "has a heading section" which {
          "has a heading" in {
            mainContent.getH1Element.text mustBe Heading.heading
          }
        }

        "has a before sign up section" which {
          "has a heading" in {
            mainContent.selectHead("h2").text mustBe BeforeSignUp.heading
          }

          "has a first paragraph" in {
            mainContent.selectNth("p", 1).text mustBe BeforeSignUp.paraOne
          }

          "has a second paragraph" in {
            mainContent.selectNth("p", 2).text mustBe BeforeSignUp.paraTwo
          }

          "has a third paragraph" in {
            mainContent.selectNth("p", 3).text mustBe BeforeSignUp.paraThree
          }
        }

        "has a accounting period section" which {
          "has a heading" in {
            mainContent.selectNth("h2", 2).text mustBe AccountingPeriod.heading
          }

          "has a first paragraph" in {
            mainContent.selectNth("p", 4).text mustBe AccountingPeriod.paraOne
          }

          def bulletList: Element = mainContent.selectNth("ul", 1)

          "has a bullet point one" in {
            bulletList.selectHead("li").text mustBe AccountingPeriod.bulletOne
          }

          "has a bullet point two" in {
            bulletList.selectNth("li", 2).text mustBe AccountingPeriod.bulletTwo
          }
        }

        "has a check sign up section" which {
          "has a heading" in {
            mainContent.selectNth("h2", 3).text mustBe CheckSignUp.heading
          }

          "has a first paragraph" in {
            mainContent.selectNth("p", 5).text mustBe CheckSignUp.paraOne
          }

          def bulletList: Element = mainContent.selectNth("ul", 2)

          "has a bullet point one" in {
            bulletList.selectHead("li").text mustBe CheckSignUp.bulletOne
          }

          "has a bullet point two" in {
            bulletList.selectNth("li", 2).text mustBe CheckSignUp.bulletTwo
          }

          "has a bullet point three" in {
            bulletList.selectNth("li", 3).text mustBe CheckSignUp.bulletThree
          }

          "has a second paragraph" in {
            mainContent.selectNth("p", 6).text mustBe CheckSignUp.paraTwo
          }
        }

        "has a continue button" in {
          doc.select("button").last().text mustBe Base.continue
        }

        "has the correct form" in {
          doc.getForm must have(
            method("POST"),
            action(routes.SigningUpController.submit.url)
          )
        }
      }
    }
  }

  s"POST $path" should {
    "redirect to the start of the agent sign up" in {
      submitResult must have(
        httpStatus(SEE_OTHER),
        redirectUri(appConfig.incomeTaxSubscriptionFrontendAgentIncomeSourcesPageFullUrl)
      )
    }
  }
}
