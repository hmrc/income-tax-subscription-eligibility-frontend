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
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.AgentSignupTerms.AgentHeading._
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.AgentSignupTerms.AgentHowToSignUp.AgentCompleteSignUp
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.AgentSignupTerms._
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{Base, agentSuffix}
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
        doc.title mustBe s"${AgentHeading.heading}$agentSuffix"
      }

      "return a view" which {
        "uses the correct template details" in new TemplateViewTest(
          document = doc,
          title = heading,
          isAgent = true,
          backLink = Some(appConfig.govukGuidanceITSASignUpAgentLink)
        )(appConfig)
      }

      "return a view with a title" in {
        doc.title mustBe s"$heading$agentSuffix"
      }


      "has main content" which {
        val mainContent = doc.getElementById("main-content")
        "has a heading section" which {
          import AgentHeading._
          "has a heading" in {
            mainContent.getH1Element.text mustBe heading
          }
          "has a first para" in {
            mainContent.select("p").get(0).text mustBe para1
          }

          "has a second para" in {
            mainContent.select("p").get(1).text mustBe para2
          }

          "has a first bullet point" in {
            mainContent.selectFirst("ul").select("li").get(0).text mustBe bullet1
          }
          "has a second bullet point" in {
            mainContent.selectFirst("ul").select("li").get(1).text mustBe bullet2
          }

          "has a third para" in {
            mainContent.select("p").get(2).text mustBe para3
          }

        }

        "has a how to sign up section" which {
          "has a heading section" which {
          }

          "has a Get your clients ready section" which {
            import AgentHowToSignUp.AgentGetReady._
            "has a heading" in {
              mainContent.select("h3").get(0).text mustBe heading
            }
            "has a para" in {
              mainContent.select("p").get(3).text mustBe para
            }
          }

          "has a Check their eligibility section" which {
            import AgentHowToSignUp.AgentCheckAvailability._
            "has a heading" in {
              mainContent.select("h3").get(1).text mustBe heading
            }
            "has a para" in {
              mainContent.select("p").get(4).text mustBe para
            }

          }

          "has a Get your client’s information ready section" which {
            import AgentHowToSignUp.AgentGetInformation._
            "has a heading" in {
              mainContent.select("h3").get(2).text mustBe heading
            }

            "has a para" in {
              mainContent.select("p").get(5).text mustBe para
            }

            "has a first bullet point" in {
              mainContent.selectNth("ul", 2).select("li").get(0).text mustBe bullet1
            }
            "has a second bullet point" in {
              mainContent.selectNth("ul", 2).select("li").get(1).text mustBe bullet2
            }
            "has a third bullet point" in {
              mainContent.selectNth("ul", 2).select("li").get(2).text mustBe bullet3
            }
            "has a fourth bullet point" in {
              mainContent.selectNth("ul", 2).select("li").get(3).text mustBe bullet4
            }
          }

          "has a Confirm client details section" which {
            import AgentHowToSignUp.AgentConfirmClientDetails._
            "has a heading" in {
              mainContent.select("h3").get(3).text mustBe heading
            }

            "has a first para" in {
              mainContent.select("p").get(6).text mustBe para1
            }

            "has a second para" in {
              mainContent.select("p").get(7).text mustBe para2
            }

            "has a first bullet point" in {
              mainContent.selectFirst("ol").selectNth("ul", 2).select("li").get(0).text mustBe bullet1
            }
            "has a second bullet point" in {
              mainContent.selectFirst("ol").selectNth("ul", 2).select("li").get(1).text mustBe bullet2
            }

            "has a first inset para" in {
              mainContent.selectNth("p", 9).text mustBe para3
            }

            "has a second inset para" in {
              mainContent.selectNth("p", 10).text mustBe para4
            }
          }
        }

        "has a Complete Sign Up section" which {
          import AgentHowToSignUp.AgentCompleteSignUp._
          "has a heading" in {
            mainContent.select("h3").get(4).text mustBe heading
          }

          "has a para" in {
            mainContent.select("p").get(10).text mustBe AgentCompleteSignUp.para
          }

          "has a first bullet point" in {
            mainContent.select("ol").select("ul").get(2).select("li").get(0).text mustBe bullet1
          }

          "has a second bullet point" in {
            mainContent.select("ol").select("ul").get(2).select("li").get(1).text mustBe bullet2
          }

          "has a third bullet point" in {
            mainContent.select("ol").select("ul").get(2).select("li").get(2).text mustBe bullet3
          }
        }

        "has a Confirm sign-up section" which {
          import AgentHowToSignUp.AgentConfirm._
          "has a heading" in {
            mainContent.select("h3").get(5).text mustBe heading
          }

          "has a paragraph" in {
            mainContent.selectNth("p", 12).text mustBe para
          }

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

  s"POST $path" should {
    "redirect to the start of the agent sign up" in {
      submitResult must have(
        httpStatus(SEE_OTHER),
        redirectUri(appConfig.incomeTaxSubscriptionFrontendAgentIncomeSourcesPageFullUrl)
      )
    }
  }
}