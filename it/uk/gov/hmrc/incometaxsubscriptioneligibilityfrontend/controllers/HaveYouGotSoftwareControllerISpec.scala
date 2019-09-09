
package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers

import org.jsoup.Jsoup
import play.api.test.Helpers._
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{HaveYouGotSoftware => messages}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class HaveYouGotSoftwareControllerISpec extends ComponentSpecBase with ViewSpec {

  val uri = "/have-you-got-software"

  "GET /have-you-got-software" should {
    lazy val res = get(uri)
    lazy val doc = Jsoup.parse(res.body)

    "return OK" in {
      res must have (
        httpStatus(OK)
      )
    }
    s"have the title ${messages.title}" in {
      doc.title mustBe messages.title
    }
    s"have the hint text ${messages.hint}" in {
      doc.getParagraphs must have(
        elementWithValue(messages.hint)
      )
    }
    "be an interrupt card" in {
      doc.select(".interrupt-card").isEmpty() mustBe false
    }
    "have two-thirds layout" in {
      doc.select(".interrupt-card").first().child(0).hasClass("column-two-thirds") mustBe true
    }
    "have a form with two radio buttons and a submit button" in {
      val form = doc.select("form")

      form.isEmpty() mustBe false

      val radios = form.select("input[type=radio]")

      radios.size() mustBe 2
      radios.get(0).attr("id") mustBe "yes"
      radios.get(1).attr("id") mustBe "no"

      val submitButton = form.select("button[type=submit]")

      submitButton must have(
        text(messages.continue)
      )
    }
    "have labels associated with each radio button" in {
      val labels = doc.select("form").select("label")

      labels.get(0).attr("for") mustBe "yes"
      labels.get(1).attr("for") mustBe "no"
    }
  }

  "POST /have-you-got-software" when {
    "the answer is Yes" should {
      "redirect to the \"Check software is compatible\" page" in {
        lazy val res = post(uri)("yes-no" -> "yes")

        res must have(
          httpStatus(SEE_OTHER),
          redirectUri(routes.CheckCompatibleSoftwareController.show().url)
        )
      }
    }
    "the answer is No" should {
      "redirect to the \"You will need to get software\" page" in {
        lazy val res = post(uri)("yes-no" -> "no")

        res must have(
          httpStatus(SEE_OTHER),
          redirectUri(routes.GetSoftwareController.show().url)
        )
        /*
        res must have(
          httpStatus(NOT_IMPLEMENTED),
          redirectUri(routes.CheckCompatibleSoftwareController.show().url)
        )
         */
      }
    }
    "no answer is provided" should {
      "return BAD REQUEST" in {
        lazy val res = post(uri)("yes-no" -> "")

        res must have(
          httpStatus(BAD_REQUEST)
        )
      }
      "contain the error text"  in {
        lazy val res = post(uri)("yes-no" -> "")
        lazy val doc = Jsoup.parse(res.body)
        val errorMessage = doc.select("span[class=form-field--error]")
        errorMessage.isEmpty mustBe false
        errorMessage.text() mustBe "Select yes if you currently use accounting software"
      }
    }
  }

}
