
package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers.principal

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import play.api.test.Helpers.OK
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{CovidCannotSignup => messages}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class CovidCannotSignupControllerISpec extends ComponentSpecBase with ViewSpec {

  "Get /error/covid-cannot-sign-up" should{

    lazy val result = get("/error/covid-cannot-sign-up")
    lazy val doc: Document = Jsoup.parse(result.body)
    lazy val content: Element = doc.mainContent

    "return OK" in {
      result must have(
        httpStatus(OK)
      )
    }

    "have the correct template details" in new TemplateViewTest(doc, messages.heading)(appConfig)

    "have a view with the correct heading" in {
      content.getH1Element.text mustBe messages.heading
    }

    "have a view with the correct first paragraph" in {
      content.select("p:nth-of-type(1)").text mustBe messages.para1
    }

    "have a view with the correct title Self Assessment Paragraph and link" in {
      content.select("p:nth-of-type(2)").text mustBe messages.SelfAssessment
      content.select("a[class=govuk-link]").text mustBe messages.SelfAssessmentLink
      content.select("a[class=govuk-link]").attr("href") mustBe messages.SelfAssessmentHref
    }
  }

}
