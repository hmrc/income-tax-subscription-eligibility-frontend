package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers

import play.api.libs.ws.WSResponse
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class LanguageControllerISpec extends ComponentSpecBase with ViewSpec {

  lazy val resultCy: WSResponse = get("/language/cymraeg")

  "GET /language/cymraeg" should {
    "update the PLAY_LANG cookie to cy" in {
      resultCy.headers.isDefinedAt("Set-Cookie") mustBe true
      resultCy.headers.toString.contains("PLAY_LANG=cy;") mustBe true
      }
  }

  lazy val resultEn: WSResponse = get("/language/english")

  "GET /language/english" should {
    "update the PLAY_LANG cookie to en" in {
      resultEn.headers.isDefinedAt("Set-Cookie") mustBe true
      resultEn.headers.toString.contains("PLAY_LANG=en;") mustBe true
    }
  }

}
