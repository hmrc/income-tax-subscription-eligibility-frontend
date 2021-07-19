
package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils

import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements
import org.scalatest.MustMatchers
import org.scalatest.matchers.{HavePropertyMatchResult, HavePropertyMatcher}
import play.api.data.FormError

import scala.collection.JavaConversions._

trait ViewSpec extends MustMatchers {

  class TemplateViewTest(document: Document,
                         title: String,
                         isAgent: Boolean = false,
                         backLink: Option[String] = None,
                         hasSignOutLink: Boolean = false,
                         error: Option[FormError] = None) {

    private val titlePrefix: String = if (error.isDefined) "Error: " else ""
    private val titleSuffix: String = if (isAgent) {
      " - Use software to report your client’s Income Tax - GOV.UK"
    } else {
      " - Use software to send Income Tax updates - GOV.UK"
    }

    document.title mustBe s"$titlePrefix$title$titleSuffix"

    backLink.map { href =>
      val link = document.selectHead(".govuk-back-link")
      link.text mustBe "Back"
      link.attr("href") mustBe href
    }

    error.map { formError =>
      val errorSummary: Element = document.selectHead(".govuk-error-summary")
      errorSummary.selectHead("h2").text mustBe "There is a problem"
      val errorLink: Element = errorSummary.selectHead("div > ul > li > a")
      errorLink.text mustBe formError.message
      errorLink.attr("href") mustBe s"#${formError.key}"
    }

  }

  implicit class ElementTest(element: Element) {

    def selectHead(selector: String): Element = {
      element.select(selector).headOption match {
        case Some(element) => element
        case None => fail(s"No elements returned for selector: $selector")
      }
    }

    def selectOptionally(selector: String): Option[Element] = {
      element.select(selector).headOption
    }

    def content: Element = element.selectFirst("article")

    def mainContent: Element = element.selectFirst("main")

    def getParagraphs: Elements = element.getElementsByTag("p")

    def getBulletPoints: Elements = element.getElementsByTag("li")

    def getH1Element: Elements = element.getElementsByTag("h1")

    def getH2Elements: Elements = element.getElementsByTag("h2")

    def getFormElements: Elements = element.getElementsByClass("form-field-group")

    def getErrorSummaryMessage: Elements = element.select("#error-summary-display ul")

    def getSubmitButton: Elements = element.select("button[type=submit]")

    def getHintText: String = element.select(s"""[class=form-hint]""").text()

    def getForm: Elements = element.select("form")

    def getSpan(id: String): Elements = element.select(s"""span[id=$id]""")

    def getLink(id: String): Elements = element.select(s"""a[id=$id]""")

    def getTextFieldInput(id: String): Elements = element.select(s"""input[id=$id]""")

    def getFieldErrorMessage(id: String): Elements = element.select(s"""a[id=$id-error-summary]""")

    def getBulletPointList: Elements = element.select("ul[class=list list-bullet]")

  }

  def value(value: String): HavePropertyMatcher[Elements, String] =
    new HavePropertyMatcher[Elements, String] {
      def apply(element: Elements) =
        HavePropertyMatchResult(
          element.`val`() == value,
          "value",
          value,
          element.`val`()
        )
    }

  def href(url: String): HavePropertyMatcher[Elements, String] =
    new HavePropertyMatcher[Elements, String] {
      def apply(element: Elements) =
        HavePropertyMatchResult(
          element.attr("href") == url,
          "href",
          url,
          element.attr("href")
        )
    }

  def method(method: String): HavePropertyMatcher[Elements, String] =
    new HavePropertyMatcher[Elements, String] {
      def apply(element: Elements) =
        HavePropertyMatchResult(
          element.attr("method") == method,
          "method",
          method,
          element.attr("method")
        )
    }

  def action(action: String): HavePropertyMatcher[Elements, String] =
    new HavePropertyMatcher[Elements, String] {
      def apply(element: Elements) =
        HavePropertyMatchResult(
          element.attr("action") == action,
          "action",
          action,
          element.attr("action")
        )
    }

  def text(text: String): HavePropertyMatcher[Elements, String] =
    new HavePropertyMatcher[Elements, String] {
      def apply(element: Elements) =
        HavePropertyMatchResult(
          element.text() == text,
          "text",
          text,
          element.text()
        )
    }

  def label(label: String): HavePropertyMatcher[Elements, String] =
    new HavePropertyMatcher[Elements, String] {
      def apply(element: Elements) = {
        val labelElem = element.parents().get(0).tagName("label")
        val labelForAttr = labelElem.attr("for")

        HavePropertyMatchResult(
          labelForAttr == element.attr("id"),
          "label for attr",
          element.attr("id"),
          labelForAttr
        )

        HavePropertyMatchResult(
          labelElem.text() == label,
          "label text",
          label,
          labelElem.text()
        )
      }
    }

  def elementWithValue(value: String): HavePropertyMatcher[Elements, String] =
    new HavePropertyMatcher[Elements, String] {
      def apply(element: Elements) = {
        val elem = element.select(s":contains($value)")

        HavePropertyMatchResult(
          elem.text() == value,
          "paragraph",
          value,
          elem.text()
        )
      }
    }

  def option(id: String, value: String): HavePropertyMatcher[Elements, String] =
    new HavePropertyMatcher[Elements, String] {
      def apply(element: Elements) = {
        val span = element.select(s"option[id=$id]")

        HavePropertyMatchResult(
          span.text() == value,
          s"option $id",
          value,
          span.text()
        )
      }
    }

  def errorSummaryMessage(id: String, message: String): HavePropertyMatcher[Elements, String] =
    new HavePropertyMatcher[Elements, String] {
      def apply(element: Elements) = {
        val errorMessage = element.select(s"a[id=$id-error-summary]")

        HavePropertyMatchResult(
          errorMessage.text() == message,
          "error summary errors",
          message,
          errorMessage.text()
        )
      }
    }

  def errorMessage(message: String): HavePropertyMatcher[Elements, String] =
    new HavePropertyMatcher[Elements, String] {
      def apply(element: Elements) = {
        val errorMessage = element.parents.first.select("div[class=error-notification")

        HavePropertyMatchResult(
          errorMessage.text() == message,
          "input error message",
          message,
          errorMessage.text()
        )
      }
    }

}