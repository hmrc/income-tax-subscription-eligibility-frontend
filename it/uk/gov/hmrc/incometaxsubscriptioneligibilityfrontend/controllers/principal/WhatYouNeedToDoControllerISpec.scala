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
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{Base => commonMessages, WhatYouNeedToDo => messages}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class WhatYouNeedToDoControllerISpec extends ComponentSpecBase with ViewSpec {

  lazy val result: WSResponse = get("/what-you-need-to-do")
  lazy val doc: Document = Jsoup.parse(result.body)
  lazy val mainContent: Element = doc.mainContent

  s"GET ${routes.WhatYouNeedToDoController.show.url}" should {

    "return OK" in {
      result must have(
        httpStatus(OK)
      )
    }

    "return a HTML view" which {

      "has a title" in {
        doc.title mustBe messages.title
      }

      "has a heading" in {
        mainContent.getH1Element.text mustBe messages.heading
      }

      "has a first paragraph" in {
        mainContent.selectNth("p", 1).text mustBe messages.paraOne
      }

      "has a 2026 paragraph" in {
        mainContent.selectNth("p", 2).text mustBe messages.yearOnePara
      }

      "has a 2026 bullet list" which {
        val list = mainContent.selectNth("ul", 1)

        "has a bullet one with a link to " in {
          val bullet = list.selectNth("li", 1)
          bullet.text mustBe messages.yearOneBulletOne
          val link = bullet.selectHead("a")
          link.text mustBe messages.yearOneBulletOneLinkText
          link.attr("href") mustBe appConfig.sendingReturnGovUkUrl
          link.attr("target") mustBe "_blank"
        }

        "has a bullet two" in {
          list.selectNth("li", 2).text mustBe messages.yearOneBulletTwo
        }

        "has a bullet three" in {
          list.selectNth("li", 3).text mustBe messages.yearOneBulletThree
        }

      }

      "has a 2027 paragraph" in {
        mainContent.selectNth("p", 3).text mustBe messages.yearTwoPara
      }

      "has a 2027 bullet list" which {
        val list = mainContent.selectNth("ul", 2)

        "has a bullet one" in {
          list.selectNth("li", 1).text mustBe messages.yearTwoBulletOne
        }

        "has a bullet two" in {
          list.selectNth("li", 2).text mustBe messages.yearTwoBulletTwo
        }

        "has a bullet three" in {
          list.selectNth("li", 3).text mustBe messages.yearTwoBulletThree
        }

      }

      "has a final paragraph" in {
        mainContent.selectNth("p", 4).text mustBe messages.paraTwo
      }

      "has a form" which {
        val form: Element = mainContent.getForm

        "has the correct attributes" in {
          form.attr("method") mustBe "POST"
          form.attr("action") mustBe routes.WhatYouNeedToDoController.submit.url
        }

        "has an accept and continue button" in {
          form.selectHead("button").text mustBe commonMessages.acceptAndContinue
        }

      }

      "has a back link" in {
        val link = doc.selectHead(".govuk-back-link")
        link.attr("href") mustBe routes.SignUpController.show.url
        link.text mustBe commonMessages.backUrl
      }

    }

  }

  s"POST ${routes.WhatYouNeedToDoController.submit}" should {
    s"redirect the user to ${appConfig.incometaxSubscriptionFrontendFirstPageFullUrl}" in {
      val submit = post("/what-you-need-to-do")()
      submit must have(
        httpStatus(SEE_OTHER),
        redirectUri(appConfig.incometaxSubscriptionFrontendFirstPageFullUrl)
      )
    }
  }

}
