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
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{suffix, Base => CommonMessages, IndividualSignUpTerms => Messages}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.services.AccountingPeriodService
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class SigningUpControllerISpec extends ComponentSpecBase with ViewSpec {

  private val accountingPeriodService = new AccountingPeriodService()

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
        title = Messages.heading,
        isAgent = false,
        backLink = Some(appConfig.govukGuidanceITSASignUpIndivLink)
      )

      "has a title" in {
        doc.title mustBe s"${Messages.heading}$suffix"
      }

      "has a main heading" in {
        doc.mainContent.getH1Element.text mustBe Messages.heading
      }

      "has a first paragraph" in {
        doc.mainContent.selectHead("p").text mustBe Messages.Intro.paraOne
      }

      "has an inset text" which {
        def inset: Element = doc.mainContent.selectHead(".govuk-inset-text")

        "has a first paragraph" in {
          inset.selectHead("p").text mustBe Messages.Intro.Inset.paraOne
        }
        "has a bullet list" which {
          def bulletList: Element = inset.selectHead("ul")

          "has a first bullet" in {
            bulletList.selectNth("li", 1).text mustBe Messages.Intro.Inset.bulletOne
          }
          "has a second bullet" in {
            bulletList.selectNth("li", 2).text mustBe Messages.Intro.Inset.bulletTwo
          }
        }
        "has a second paragraph" in {
          inset.selectNth("p", 2).text mustBe Messages.Intro.Inset.paraTwo
        }
      }

      "has a second paragraph" in {
        doc.mainContent.selectNth("p", 4).text mustBe Messages.Intro.paraTwo
      }

      "has a bullet list" which {
        def bulletList: Element = doc.mainContent.selectNth("ul", 2)

        "has a first bullet" in {
          bulletList.selectNth("li", 1).text mustBe Messages.Intro.bulletOne
        }
        "has a second bullet" in {
          bulletList.selectNth("li", 2).text mustBe Messages.Intro.bulletTwo
        }
      }

      "has a section 1" that {
        val section1 =
          doc
            .mainContent
            .selectHead("ol")
            .selectNth("li", 1)

        "contains a header" in {
          section1.selectHead("h3").text mustBe Messages.SectionOne.heading
        }

        "contains a paragraph" in {
          section1.selectHead("p").text mustBe Messages.SectionOne.paragraph
        }
      }

      "has a section 2" that {
        val section2 =
          doc
            .mainContent
            .selectHead("ol")
            .selectNth("li", 2)

        "contains a header" in {
          section2.selectHead("h3").text mustBe Messages.SectionTwo.heading
        }

        "contains paragraph 1" in {
          section2.selectNth("p", 1).text mustBe Messages.SectionTwo.paragraph1
        }

        "contains paragraph 2" in {
          section2.selectNth("p", 2).text mustBe Messages.SectionTwo.paragraph2
        }

        "contains bullet 1" in {
          section2.selectNth("ul li", 1).text mustBe Messages.SectionTwo.bullet1(accountingPeriodService.currentTaxYear)
        }

        "contains bullet 2" in {
          section2.selectNth("ul li", 2).text mustBe Messages.SectionTwo.bullet2(accountingPeriodService.nextTaxYear)
        }

        "contains inset text" in {
          section2.selectHead(".govuk-inset-text").text mustBe Messages.SectionTwo.insetText
        }

        "contains paragraph 3" in {
          section2.selectNth("p", 3).text mustBe Messages.SectionTwo.paragraph3
        }
      }

      "has a section 3" that {
        val section3 =
          doc
            .mainContent
            .selectHead("ol")
            .selectNth("li", 5)

        "contains a header" in {
          section3.selectHead("h3").text mustBe Messages.SectionThree.heading
        }

        "contains paragraph 1" in {
          section3.selectNth("p", 1).text mustBe Messages.SectionThree.paragraph1
        }

        "contains bullet 1" in {
          section3.selectNth("ul li", 1).text mustBe Messages.SectionThree.bullet1
        }

        "contains bullet 2" in {
          section3.selectNth("ul li", 2).text mustBe Messages.SectionThree.bullet2
        }

        "contains bullet 3" in {
          section3.selectNth("ul li", 3).text mustBe Messages.SectionThree.bullet3
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
          form.selectHead("button").text mustBe CommonMessages.continue
        }
      }

    }
  }

  s"POST $path" should {
    "redirect to the start of the agent sign up" in {
      submitResult must have(
        httpStatus(SEE_OTHER),
        redirectUri(routes.HaveAnyOtherIncomeController.show.url)
      )
    }
  }
}
