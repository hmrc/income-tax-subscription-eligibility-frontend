
package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers.principal

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{suffix, CovidCannotSignup => messages}
import play.api.test.Helpers.OK
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class CovidCannotSignupControllerISpec extends ComponentSpecBase with ViewSpec {

  "Get /error/covid-cannot-sign-up" should{

    lazy val result = get("/error/covid-cannot-sign-up")
    lazy val doc: Document = Jsoup.parse(result.body)
    lazy val content: Element = doc.content

    "return OK" in {
      result must have(
        httpStatus(OK)
      )
    }

    "have a view with the correct title" in {
      doc.title mustBe s"${messages.title}${suffix}"
    }

    "have a view with the correct heading" in {
      content.getH1Element.text mustBe messages.heading
    }

    "have a view with the correct first paragraph" in {
      content.select("p:nth-of-type(1)").text mustBe messages.para1
    }

    "have a view with the correct title Self Assessment Paragraph and link" in {
      content.select("p:nth-of-type(2)").text mustBe messages.SelfAssessment
      content.select("a").text mustBe messages.SelfAssessmentLink
      content.select("a").attr("href") mustBe messages.SelfAssessmentHref
    }
  }

}
