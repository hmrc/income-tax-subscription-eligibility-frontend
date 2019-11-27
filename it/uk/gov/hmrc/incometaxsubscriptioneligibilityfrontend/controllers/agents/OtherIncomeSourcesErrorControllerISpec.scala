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
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}
import play.api.test.Helpers._
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.OtherSourcesOfIncomeError._

class OtherIncomeSourcesErrorControllerISpec extends ComponentSpecBase with ViewSpec {

  lazy val result: WSResponse = get("/client/other-sources-of-income-error")
  lazy val doc: Document = Jsoup.parse(result.body)

  "GET /client/other-sources-of-income-error" should {

    "return a status of OK" in {
      result.status mustBe OK
    }

    "return a page" which {

      "has the correct title" in {
        doc.title mustBe title
      }

      "has the correct heading" in {
        doc.select("h1").text() mustBe heading
      }

      "has a correct leading paragraph" in {
        doc.select("article p").first().text() mustBe para1
      }

      "has a bullet point list" which {
        lazy val bulletPoints = doc.select("article ul li")
        "has three elements" in {
          bulletPoints.size() mustBe 3
        }

        "has the correct first point" in {
          bulletPoints.get(0).text() mustBe bullet1
        }

        "has the correct second point" in {
          bulletPoints.get(1).text() mustBe bullet2
        }

        "has the correct third point" in {
          bulletPoints.get(2).text() mustBe bullet3
        }
      }

      "has a correct final paragraph" which {

        "has the correct text" in {
          doc.select("article p").last().text() mustBe para2
        }

        "has a link" which {
          lazy val link = doc.select("article p a")

          "has the correct text" in {
            link.text() mustBe linkText
          }

          "has the correct ref" in {
            link.attr("href") mustBe "https://www.gov.uk/self-assessment-tax-returns/sending-return"
          }
        }
      }
    }
  }
}
