/*
 * Copyright 2021 HM Revenue & Customs
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

import play.api.http.Status
import play.api.libs.ws.WSResponse
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers.agents.{routes => agentRoutes}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class  LanguageSwitchControllerISpec extends ComponentSpecBase with ViewSpec {

  val testRefererRoute: String = "/test/referer/route"

  "GET /language/cymraeg" should {
    "update the PLAY_LANG cookie to cy and return the user where they were when a REFERER is in the headers" in {
      lazy val resultCy: WSResponse = getWithHeaders("/client/language/cymraeg", "REFERER" -> testRefererRoute)
      resultCy.headers.isDefinedAt("Set-Cookie") mustBe true
      resultCy.headers.toString.contains("PLAY_LANG=cy;") mustBe true
      resultCy must have(
        httpStatus(Status.SEE_OTHER),
        redirectUri(testRefererRoute)
      )
    }

    "update the PLAY_LANG cookie to cy and return the user to the overview page when REFERER is not in the headers" in {
      lazy val resultCy: WSResponse = getWithHeaders("/client/language/cymraeg")
      resultCy.headers.isDefinedAt("Set-Cookie") mustBe true
      resultCy.headers.toString.contains("PLAY_LANG=cy;") mustBe true
      resultCy must have(
        httpStatus(Status.SEE_OTHER),
        redirectUri(agentRoutes.IndexController.index.url)
      )
    }
  }

  "GET /language/english" should {
    "update the PLAY_LANG cookie to en and return the user where they were when a REFERER is in the headers" in {
      lazy val resultEn: WSResponse = getWithHeaders("/client/language/english", "REFERER" -> testRefererRoute)
      resultEn.headers.isDefinedAt("Set-Cookie") mustBe true
      resultEn.headers.toString.contains("PLAY_LANG=en;") mustBe true
      resultEn must have(
        httpStatus(Status.SEE_OTHER),
        redirectUri(testRefererRoute)
      )
    }
    "update the PLAY_LANG cookie to en and return the user to the overview page when REFERER is not in the headers" in {
      lazy val resultEn: WSResponse = getWithHeaders("/client/language/english")
      resultEn.headers.isDefinedAt("Set-Cookie") mustBe true
      resultEn.headers.toString.contains("PLAY_LANG=en;") mustBe true
      resultEn must have(
        httpStatus(Status.SEE_OTHER),
        redirectUri(agentRoutes.IndexController.index.url)
      )
    }
  }

}
