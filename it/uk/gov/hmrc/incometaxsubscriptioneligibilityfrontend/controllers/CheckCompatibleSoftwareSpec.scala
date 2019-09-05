
package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.AppConfig
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{CheckCompatibleSoftware => messages, Base => base}
import play.api.test.Helpers._

class CheckCompatibleSoftwareSpec extends ComponentSpecBase with ViewSpec with GuiceOneServerPerSuite {

  lazy val result = get("/check-compatible-software")
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