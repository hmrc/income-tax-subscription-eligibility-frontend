
package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import play.api.libs.ws.WSResponse
import play.api.test.Helpers._
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{Overview => messages}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class OverviewControllerISpec extends ComponentSpecBase with ViewSpec {

  lazy val result: WSResponse = get("/overview")
  lazy val doc: Document = Jsoup.parse(result.body)

  "GET /overview" should {
    "return OK" in {
      result must have(
        httpStatus(OK)
      )
    }

    "return a view with a title" in {
      doc.title mustBe messages.title
    }

    "return a view with a heading" in {
      doc.getH1Element.text mustBe messages.heading
    }

    "return a view with two paragraphs" in {
      doc.getParagraphs must have(
        elementWithValue(messages.line1),
        elementWithValue(messages.line2)
      )
    }

    "return a view with a link" in {
      val link = doc.getLink("guidance")

      link.text mustBe messages.linkText
      link.attr("href") mustBe appConfig.guidanceUrl
    }

    "return a view with a continue button" in {
      doc.getSubmitButton.text mustBe messages.button
    }

    "have the correct form" in {
      doc.getForm must have(
        method("GET"),
        action(routes.OverviewController.show().url)
        // TODO the action should be the do you have software controller
      )
    }
  }

}
