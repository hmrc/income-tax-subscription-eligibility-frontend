
package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import play.api.libs.ws.WSResponse
import play.api.test.Helpers._
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{GetSoftware => messages}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}


class GetSoftwareControllerISpec extends ComponentSpecBase with ViewSpec {

  lazy val result: WSResponse = get("/get-software")
  lazy val doc: Document = Jsoup.parse(result.body)

  "GET /get-software" should {
    "return OK" in {
      result must have(
        httpStatus(OK)
      )
    }

    "have the correct title" in {
      doc.title mustBe messages.title
    }

    "have the correct header" in {
      doc.getH1Element.text mustBe messages.header
    }

    "have the correct text" in {
      doc.getParagraphs must have(
        elementWithValue(messages.line1)
      )
    }

    "have the continue button" in {
      doc.getSubmitButton.text() mustBe messages.continue
    }

    "have the correct hyperlink" in {
      doc.getLink("find-software-link") must have(
        text(messages.findSoftware),
        href(messages.findSoftwareLink)
      )
    }

    "have the correct form" in {
      doc.getForm must have(
        method("GET"),
        action(routes.TermsController.show().url)
      )
    }
  }
}
