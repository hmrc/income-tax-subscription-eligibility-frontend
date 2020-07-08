
package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers.agents

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import play.api.test.Helpers.OK
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{Base => base, AgentsCovidCannotSignUp => messages}


class CovidCannotSignUpControllerISpec extends ComponentSpecBase with ViewSpec {
  "GET /client/error/covid-cannot-sign-up" should {
    lazy val result = get("/client/error/covid-cannot-sign-up")
    lazy val doc: Document = Jsoup.parse(result.body)
    lazy val content: Element = doc.content

    "return OK" in {
      result must have(
        httpStatus(OK)
      )
    }
    "have a view with the correct title" in {
      doc.title mustBe messages.title
    }

    "have a view with the correct heading" in {
      doc.getH1Element.text mustBe messages.heading
    }

    "return a view with the first paragraph" in {
      content.select("p:nth-of-type(1)").text mustBe messages.line1
    }


    "have a view with the correct second Paragraph and link" in {
      content.select("p:nth-of-type(2)").text mustBe messages.line2
      content.select("a").text mustBe messages.link
      content.select("a").attr("href") mustBe messages.linkHref
    }

    "return a view with a Sign Up Another Client button" in {
      doc.getSubmitButton.text mustBe messages.signUpAnotherClient
    }


  }


}
