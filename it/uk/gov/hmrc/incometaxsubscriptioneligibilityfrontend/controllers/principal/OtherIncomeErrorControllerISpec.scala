package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers.principal

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import play.api.test.Helpers.OK
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{suffix, HaveAnyOtherIncomeError => messages}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class OtherIncomeErrorControllerISpec extends ComponentSpecBase with ViewSpec {
  "GET /error/other-income-error" should {
    lazy val result = get("/eligibility/error/other-income-error")
    lazy val doc: Document = Jsoup.parse(result.body)

    "return OK" in {
      result must have(
        httpStatus(OK)
      )
    }

    "have a view with the correct title" in {
      doc.title mustBe s"${messages.title}${suffix}"
    }

    "have a view with the correct heading" in {
      doc.getH1Element.text mustBe messages.title
    }

    "have a view with two correct paragraph lines" in {
      doc.getParagraphs.text().contains(messages.line1) mustBe true
    }
  }
}
