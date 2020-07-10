
package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers.principal

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import play.api.libs.ws.WSResponse
import play.api.test.Helpers._
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{suffix, Base => base, Overview => messages}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers.routes
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class OverviewControllerISpec extends ComponentSpecBase with ViewSpec {


  "GET /overview" should {

    lazy val result = get("/overview")
    lazy val doc: Document = Jsoup.parse(result.body)
    lazy val content: Element = doc.content


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

    "return a view with the correct second heading" in {
      content.getH2Elements.text mustBe messages.heading2
    }


    "return a view with the first paragraph" in {
      content.select("p:nth-of-type(1)").text mustBe messages.line1
    }

    "return a view second paragraph with a link" in {
      val link = doc.getLink("guidance")

      link.text mustBe messages.linkText
      link.attr("href") mustBe appConfig.guidanceUrl
    }

    "return a view with the third paragraph" in {
      content.select("p:nth-of-type(4)").text mustBe messages.line3
    }

    "return a view with four bullet points" in {
      content.select("ul:nth-of-type(1)").select("li:nth-of-type(1)").text mustBe messages.bullet1
      content.select("ul:nth-of-type(1)").select("li:nth-of-type(2)").text mustBe messages.bullet2
      content.select("ul:nth-of-type(1)").select("li:nth-of-type(3)").text mustBe messages.bullet3
      content.select("ul:nth-of-type(1)").select("li:nth-of-type(4)").text mustBe messages.bullet4
    }


    "return a view with a continue button" in {
      doc.getSubmitButton.text mustBe base.continue
    }

    "have the correct form" in {
      doc.getForm must have(
        method("GET"),
        action(routes.Covid19ClaimCheckController.show().url)
      )
    }


  }


}
