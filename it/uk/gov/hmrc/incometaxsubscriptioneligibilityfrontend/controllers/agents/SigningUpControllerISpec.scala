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
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{agentSuffix, AgentSignupTerms => messages, Base => commonMessages}
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
      "has a title" in {
        doc.title mustBe s"${messages.heading}$agentSuffix"
      }


      "has main content" which {
        val mainContent = doc.getElementById("main-content")
        "has a heading section" which {
          "has a heading" in {
            mainContent.getH1Element.text mustBe messages.heading
          }
          "has a first para" in {
            mainContent.select("p").get(0).text mustBe messages.para1
          }

          "has a second para" in {
            mainContent.select("p").get(1).text mustBe messages.para2
          }

          "has a third para" in {
            mainContent.select("p").get(2).text mustBe messages.para3
          }

          "has a first bullet point" in {
            mainContent.selectFirst("ul").select("li").get(0).text mustBe messages.bullet1
          }
          "has a second bullet point" in {
            mainContent.selectFirst("ul").select("li").get(1).text mustBe messages.bullet2
          }
          "has a third bullet point" in {
            mainContent.selectFirst("ul").select("li").get(2).text mustBe messages.bullet3
          }
        }

        "has a how to sign up section" which {
          "has a second heading" in {
            mainContent.getH2Elements.get(0).text mustBe messages.heading2
          }

          "has a third heading" in {
            mainContent.select("h3").get(0).text mustBe messages.heading3
          }

          "has a fourth heading" in {
            mainContent.select("h3").get(1).text mustBe messages.heading4
          }

          "has a fifth heading" in {
            mainContent.select("h3").get(2).text mustBe messages.heading5
          }

          "has a sixth heading" in {
            mainContent.select("h3").get(3).text mustBe messages.heading6
          }

          "has a first para" in {
            mainContent.select("p").get(3).text mustBe messages.para4
          }
          "has a second para" in {
            mainContent.select("p").get(4).text mustBe messages.para5
          }

          "has a third para" in {
            mainContent.select("p").get(5).text mustBe messages.para6
          }

          "has a fourth para" in {
            mainContent.select("p").get(6).text mustBe messages.para7
          }

          "has a fifth para" in {
            mainContent.select("p").get(7).text mustBe messages.para8
          }

          "has a sixth para" in {
            mainContent.select("p").get(8).text mustBe messages.para9
          }

          "has a seventh para" in {
            mainContent.select("p").get(9).text mustBe messages.para10
          }
        }

        "has a first bullet point" in {
          mainContent.selectFirst("ol").selectFirst("ul").select("li").get(0).text mustBe messages.bullet4
        }
        "has a second bullet point" in {
          mainContent.selectFirst("ol").selectFirst("ul").select("li").get(1).text mustBe messages.bullet5
        }

      }

      "has a continue button" in {
        doc.select("button").last().text mustBe commonMessages.continue
      }

      "has the correct form" in {
        doc.getForm must have(
          method("POST"),
          action(routes.SigningUpController.submit.url)
        )
      }
    }
  }

  s"POST $path" should {
    "redirect to the start of the agent sign up" in {
      submitResult must have(
        httpStatus(SEE_OTHER),
        redirectUri(routes.TermsController.show.url)
      )
    }
  }
}
