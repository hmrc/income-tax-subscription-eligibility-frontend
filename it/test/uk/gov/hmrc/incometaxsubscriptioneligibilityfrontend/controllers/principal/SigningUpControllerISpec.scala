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
import play.api.libs.ws.WSResponse
import play.api.test.Helpers._
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{Base, IndividualSignUpTerms, suffix}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class SigningUpControllerISpec extends ComponentSpecBase with ViewSpec {

  private val path = "/signing-up"
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
        title = IndividualSignUpTerms.heading,
        isAgent = false,
        backLink = Some(appConfig.govukGuidanceITSASignUpIndivLink)
      )

      "has a title" in {
        doc.title mustBe s"${IndividualSignUpTerms.heading}$suffix"
      }

      "has a main heading" in {
        doc.mainContent.getH1Element.text mustBe IndividualSignUpTerms.heading
      }

      "has a before you sign up section" that {
        import IndividualSignUpTerms.beforeYouSignUp._

        "has a heading" in {
          doc.mainContent.selectHead("h2").text mustBe heading
        }

        "Has first paragraph" in {
          doc.mainContent.selectNth("p", 1).text mustBe paraOne
        }

        "has a second paragraph" in {
          doc.mainContent.selectNth("p", 2).text mustBe paraTwo
        }

      }

      "has a sole trader section" that {
        import IndividualSignUpTerms.soleTrader._

        "has a heading" in {
          doc.mainContent.selectHead("h3").text mustBe heading
        }

        "has a first paragraph" in {
          doc.mainContent.selectNth("p", 3).text mustBe paraOne
        }

        "has a second paragraph" in {
          doc.mainContent.selectNth("p", 4).text mustBe paraTwo
        }
      }

      "has a income from properties section" that {
        import IndividualSignUpTerms.incomeProperty._

        "has a heading" in {
          doc.mainContent.selectNth("h3", 2).text mustBe heading
        }

        "has a paragraph" in {
          doc.mainContent.selectNth("p", 5).text mustBe paraOne
        }
      }

      "has an identity verification section" that {
        import IndividualSignUpTerms.identityVerification._
        "has a heading" in {
          doc.mainContent.selectNth("h2", 2).text mustBe heading
        }
        "has a paragraph" in {
          doc.mainContent.selectNth("p", 6).text mustBe paraOne
        }
        "has a bullet list" which {
          val bulletList: Element = doc.mainContent.selectHead("ul")
          "has a first point" in {
            bulletList.selectNth("li", 1).text mustBe bulletOne

          }
          "has a second point" in {
            bulletList.selectNth("li", 2).text mustBe bulletTwo
          }
        }
      }

      "has a form" which {
        def form: Element = doc.getForm

        "has the correct form attributes" in {
          form must have(
            method("POST"),
            action(routes.SigningUpController.submit.url)
          )
        }
        "has a continue button" in {
          form.selectHead("button").text mustBe Base.continue
        }
      }

    }
  }

  s"POST $path" should {
    "redirect to the start of the agent sign up" in {
      submitResult must have(
        httpStatus(SEE_OTHER),
        redirectUri(routes.CheckAccountingPeriodController.show.url)
      )
    }
  }
}
