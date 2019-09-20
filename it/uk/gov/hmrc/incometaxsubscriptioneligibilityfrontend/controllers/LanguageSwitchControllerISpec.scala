package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers

import play.api.libs.ws.WSResponse
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class LanguageSwitchControllerISpec extends ComponentSpecBase with ViewSpec {


  "GET /language/cymraeg" should {
    "update the PLAY_LANG cookie to cy" in {
      lazy val resultCy: WSResponse = get("/eligibility/language/cymraeg")
      resultCy.headers.isDefinedAt("Set-Cookie") mustBe true
      resultCy.headers.toString.contains("PLAY_LANG=cy;") mustBe true
    }
  }

  "GET /language/english" should {
    "update the PLAY_LANG cookie to en" in {
      lazy val resultEn: WSResponse = get("/eligibility/language/english")
      resultEn.headers.isDefinedAt("Set-Cookie") mustBe true
      resultEn.headers.toString.contains("PLAY_LANG=en;") mustBe true
    }
  }

  "GET /client/language/cymraeg" should {
    "update the PLAY_LANG cookie to cy" in {
      lazy val resultCy: WSResponse = get("/client/eligibility/language/cymraeg")
      resultCy.headers.isDefinedAt("Set-Cookie") mustBe true
      resultCy.headers.toString.contains("PLAY_LANG=cy;") mustBe true
    }
  }

  "GET /client/language/english" should {
    "update the PLAY_LANG cookie to en" in {
      lazy val resultEn: WSResponse = get("/client/eligibility/language/english")
      resultEn.headers.isDefinedAt("Set-Cookie") mustBe true
      resultEn.headers.toString.contains("PLAY_LANG=en;") mustBe true
    }
  }

}
