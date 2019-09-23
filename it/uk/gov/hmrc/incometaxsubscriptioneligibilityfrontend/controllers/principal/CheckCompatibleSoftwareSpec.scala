
package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers.principal

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.test.Helpers._
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{Base => base, CheckCompatibleSoftware => messages}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class CheckCompatibleSoftwareSpec extends ComponentSpecBase with ViewSpec with GuiceOneServerPerSuite {

  lazy val result = get("/eligibility/check-compatible-software")
  lazy val doc: Document = Jsoup.parse(result.body)

  "GET /check-compatible-software" should {
    "return OK" in {
      result must have(
        httpStatus(OK)
      )
    }

    "have a view with a continue button" in {
      doc.getSubmitButton.text mustBe base.continue
    }

    "have a view with a title" in {
      doc.title mustBe messages.title
    }

    "have a view with a heading" in {
      doc.getH1Element.text mustBe messages.heading
    }

    "have a view with a paragraph line" in {
      doc.getParagraphs must have(
        elementWithValue(messages.line)
      )
    }

    "have a view with a link" in {
      doc.getLink("check-compatible-software-link") must have(
        text(messages.link),
        href(messages.linkHref)
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