
package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers.agents

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import play.api.test.Helpers._
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets.MessageLookup.{Base => commonMessages, AgentHaveAnyOtherIncome => messages}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.{ComponentSpecBase, ViewSpec}

class HaveAnyOtherIncomeControllerISpec extends ComponentSpecBase with ViewSpec {

  "GET /eligibility/client/other-income" should {
    lazy val result = get("/client/other-income")
    lazy val doc: Document = Jsoup.parse(result.body)

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

    "have a view with three correct paragraph lines" in {
      doc.getParagraphs.text().contains(messages.text1) mustBe true
      doc.getParagraphs.text().contains(messages.include) mustBe true
      doc.getParagraphs.text().contains(messages.notInclude) mustBe true
    }

    "have a view with the correct bullet point list" in {
      doc.getBulletPoints.text().contains((messages.includePoints ++ messages.notIncludePoints).mkString(" ")) mustBe true
    }

    "have a view with the correct values displayed in the form" in {
      val form = doc.select("form")
      val labels = doc.select("form").select("label")

      form.isEmpty() mustBe false

      val radios = form.select("input[type=radio]")

      radios.size() mustBe 2
      radios.get(0).attr("id") mustBe "choice-Yes"
      labels.get(0).text() mustBe commonMessages.yes

      radios.get(1).attr("id") mustBe "choice-No"
      labels.get(1).text() mustBe commonMessages.no

      val submitButton = form.select("button[type=submit]")

      submitButton must have(
        text(commonMessages.continue)
      )
    }

    "have the correct form" in {
      doc.getForm must have(
        method("POST"),
        action(routes.HaveAnyOtherIncomeController.submit().url)
      )
    }
  }

  "POST /eligibility/other-income" should {

    "return SEE_OTHER when selecting yes" in {
      val result = post("/client/other-income")("yes-no" -> "yes")

      result must have(
        httpStatus(SEE_OTHER),
        redirectUri(routes.OtherIncomeSourcesErrorController.show().url)
      )
    }

    "return SEE_OTHER when selecting No" in {
      val result = post("/client/other-income")("yes-no" -> "no")

      result must have(
        httpStatus(SEE_OTHER),
        redirectUri(appConfig.incometaxSubscriptionFrontendAgentFirstPageFullUrl)
      )
    }

    "return BADREQUEST when an invalid Answer is given" in {
      val result = post("/client/other-income")("yes-no" -> " ")
      lazy val doc: Document = Jsoup.parse(result.body)

      result must have(
        httpStatus(BAD_REQUEST)
      )

      val errorMessage = doc.select("span[class=error-notification bold]")
      errorMessage.text() mustBe messages.error
    }

    "return BADREQUEST when no Answer is given" in {
      val result = post("/client/other-income")()
      lazy val doc: Document = Jsoup.parse(result.body)

      result must have(
        httpStatus(BAD_REQUEST)
      )

      val errorMessage = doc.select("span[class=error-notification bold]")
      errorMessage.text() mustBe messages.error
    }

    "have the correct form" in {
      val result = post("/client/other-income")("yes-no" -> " ")
      lazy val doc: Document = Jsoup.parse(result.body)
      doc.getForm must have(
        method("POST"),
        action(routes.HaveAnyOtherIncomeController.submit().url)

      )
    }
  }

}