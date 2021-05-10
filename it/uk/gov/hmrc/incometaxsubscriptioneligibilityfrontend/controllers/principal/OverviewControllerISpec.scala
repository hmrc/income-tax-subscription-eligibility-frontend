
package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers.principal

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import play.api.test.Helpers._
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{Base => base, Overview => messages}
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

    "return a view with the language selector" in {
      doc.selectFirst("""nav[class="hmrc-language-select"]""")
        .selectOptionally("""a[href="/report-quarterly/income-and-expenses/sign-up/eligibility/language/cymraeg"]""").isDefined mustBe true
    }

    "return a view with the first paragraph" in {
      content.select("p:nth-of-type(1)").text mustBe messages.line1
    }

    "return a view with the second paragraph" in {
      content.select("p:nth-of-type(2)").text mustBe messages.line2
    }

    "return a view with 3 bullet points" in {
      content.select("ul:nth-of-type(1)").select("li:nth-of-type(1)").text mustBe messages.bulletCompatibleSoftware1
      content.select("ul:nth-of-type(1)").select("li:nth-of-type(2)").text mustBe messages.bulletCompatibleSoftware2
      content.select("ul:nth-of-type(1)").select("li:nth-of-type(3)").text mustBe messages.bulletCompatibleSoftware3
    }

    "return a view with the third paragraph with a link" in {
      content.select("p:nth-of-type(3)").text mustBe messages.line3
      val link = doc.getLink("guidance")
      link.text mustBe messages.linkText
      link.attr("href") mustBe appConfig.guidanceUrl
    }

    "return a view with the correct second heading" in {
      content.getH2Elements.text mustBe messages.heading2
    }

    "return a view with the four paragraph" in {
      content.select("p:nth-of-type(4)").text mustBe messages.line4
    }

    "return a view with four bullet points" in {
      content.select("ul:nth-of-type(2)").select("li:nth-of-type(1)").text mustBe messages.bulletBeforeStart1
      content.select("ul:nth-of-type(2)").select("li:nth-of-type(2)").text mustBe messages.bulletBeforeStart2
      content.select("ul:nth-of-type(2)").select("li:nth-of-type(3)").text mustBe messages.bulletBeforeStart3
      content.select("ul:nth-of-type(2)").select("li:nth-of-type(4)").text mustBe messages.bulletBeforeStart4
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
