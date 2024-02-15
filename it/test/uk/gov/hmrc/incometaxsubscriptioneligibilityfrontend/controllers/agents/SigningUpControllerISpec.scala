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
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.AgentSignupTerms.HowToSignUp.{CheckEligibility, CompleteSignUp, ConfirmSignUp, GetReady}
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

      "has main content" which {
        val mainContent = doc.getElementById("main-content")
        "has a heading section" which {
          "has a heading" in {
            mainContent.getH1Element.text mustBe Heading.heading
          }
          "has a income paragraph" in {
            mainContent.selectNth("p", 1).text mustBe Heading.haveIncomeTypePara
          }

          "has a bullet list about income" which {
            def bulletList: Element = mainContent.selectNth("ul", 1)

            "has a first bullet point" in {
              bulletList.selectNth("li", 1).text mustBe Heading.haveIncomeTypeBullet1
            }
            "has a second bullet point" in {
              bulletList.selectNth("li", 2).text mustBe Heading.haveIncomeTypeBullet2
            }
          }

          "has a paragraph about accounting period" in {
            mainContent.selectNth("p", 2).text mustBe Heading.accountingPeriodPara
          }

          "has a bullet list about accounting period" which {
            def bulletList: Element = mainContent.selectNth("ul", 2)

            "has a first bullet point" in {
              bulletList.selectNth("li", 1).text mustBe Heading.accountingPeriodBullet1
            }
            "has a second bullet point" in {
              bulletList.selectNth("li", 2).text mustBe Heading.accountingPeriodBullet2
            }
          }

        }

        "has a how to sign up detail block" which {
          def details: Element = mainContent.selectHead("details")

          "has a summary" in {
            details.selectHead("summary").text mustBe HowToSignUp.heading
          }

          "has the details body" which {
            def detailsBody: Element = details.selectHead("div")

            "has numbered list" which {
              def numberedList: Element = detailsBody.selectHead("ol")

              "has a first list item (get ready to sign up)" which {
                def listItem: Element = numberedList.selectHead("ol > li:nth-of-type(1)")

                "has a heading" in {
                  listItem.selectHead("h2").text mustBe GetReady.heading
                }
                "has a must have para" in {
                  listItem.selectNth("p", 1).text mustBe GetReady.mustHavePara
                }
                "has a bullet list for the agents must have" which {
                  def bulletList: Element = listItem.selectNth("ul", 1)

                  "has a first bullet" in {
                    bulletList.selectNth("li", 1).text mustBe GetReady.mustHaveBullet1
                  }
                  "has a second bullet" in {
                    bulletList.selectNth("li", 2).text mustBe GetReady.mustHaveBullet2
                  }
                }
                "has a client has software para" in {
                  listItem.selectNth("p", 2).text mustBe GetReady.clientSoftwarePara
                }
                "has a details required for each client para" in {
                  listItem.selectNth("p", 3).text mustBe GetReady.eachClientPara
                }
                "has a bullet list about required details for each client" which {
                  def bulletList: Element = listItem.selectNth("ul", 2)

                  "has a first bullet" in {
                    bulletList.selectNth("li", 1).text mustBe GetReady.eachClientName
                  }
                  "has a second bullet" in {
                    bulletList.selectNth("li", 2).text mustBe GetReady.eachClientNino
                  }
                  "has a third bullet" in {
                    bulletList.selectNth("li", 3).text mustBe GetReady.eachClientDOB
                  }
                  "has a forth bullet" in {
                    bulletList.selectNth("li", 4).text mustBe GetReady.eachClientBusinessDetails1
                  }
                  "has a fifth bullet" in {
                    bulletList.selectNth("li", 5).text mustBe GetReady.eachClientBusinessDetails2
                  }
                  "has a sixth bullet" in {
                    bulletList.selectNth("li", 6).text mustBe GetReady.eachClientBusinessDetails3
                  }
                }
              }
              "has a second list item (check eligibility)" which {
                def listItem: Element = numberedList.selectHead("ol > li:nth-of-type(2)")

                "has a heading" in {
                  listItem.selectHead("h2").text mustBe CheckEligibility.heading
                }
                "has a para for we will check eligibility" in {
                  listItem.selectHead("p").text mustBe CheckEligibility.para
                }
                "has an inset text section" which {
                  "has content for if their client is not eligible" in {
                    listItem.selectHead(".govuk-inset-text").text mustBe CheckEligibility.cantSignUp
                  }
                }
              }
              "has a third list item (complete sign up)" which {
                def listItem: Element = numberedList.selectHead("ol > li:nth-of-type(3)")

                "has a heading" in {
                  listItem.selectHead("h2").text mustBe CompleteSignUp.heading
                }
                "has a para" in {
                  listItem.selectHead("p").text mustBe CompleteSignUp.para
                }
                "has a bullet list" which {
                  def bulletList: Element = listItem.selectHead("ul")

                  "has a first bullet" in {
                    bulletList.selectNth("li", 1).text mustBe CompleteSignUp.bullet1
                  }
                  "has a second bullet" in {
                    bulletList.selectNth("li", 2).text mustBe CompleteSignUp.bullet2
                  }
                }
              }
              "has a forth list item (confirm sign up)" which {
                def listItem: Element = numberedList.selectHead("ol > li:nth-of-type(4)")

                "has a heading" in {
                  listItem.selectHead("h2").text mustBe ConfirmSignUp.heading
                }
                "has a first para" in {
                  listItem.selectNth("p", 1).text mustBe ConfirmSignUp.para1
                }
                "has a second para" in {
                  listItem.selectNth("p", 2).text mustBe ConfirmSignUp.para2
                }
              }
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
