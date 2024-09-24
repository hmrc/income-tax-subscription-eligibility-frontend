/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils

import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements
import org.scalatest.Checkpoints.Checkpoint
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.{HavePropertyMatchResult, HavePropertyMatcher}
import org.scalatest.{Assertion, Succeeded}
import play.api.data.FormError
import uk.gov.hmrc.govukfrontend.views.Aliases.Text
import uk.gov.hmrc.govukfrontend.views.viewmodels.radios.RadioItem
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.AppConfig

import scala.jdk.CollectionConverters._

trait ViewSpec extends Matchers {

  class TemplateViewTest(document: Document,
                         title: String,
                         isAgent: Boolean = false,
                         backLink: Option[String] = None,
                         error: Option[FormError] = None)(implicit appConfig: AppConfig) {

    private val titlePrefix: String = if (error.isDefined) "Error: " else ""
    private val titleSuffix: String = if (isAgent) {
      " - Use software to report your client’s Income Tax - GOV.UK"
    } else {
      " - Use software to send Income Tax updates - GOV.UK"
    }

    document.title mustBe s"$titlePrefix$title$titleSuffix"

    private val serviceName: String = if (isAgent) "Use software to report your client’s Income Tax" else "Use software to send Income Tax updates"
    private val serviceNameUrl: String = if (isAgent) appConfig.govukGuidanceITSASignUpAgentLink else appConfig.govukGuidanceITSASignUpIndivLink

    document.getElementsByClass("govuk-header__service-name").text() mustBe serviceName

    document.getElementsByClass("govuk-header__service-name").attr("href") mustBe serviceNameUrl

    backLink.fold(document.selectOptionally(".govuk-back-link") mustBe None) { href =>
      val link = document.selectHead(".govuk-back-link")
      link.text mustBe "Back"
      link.attr("href") mustBe href
    }

    error.map { formError =>
      val errorSummary: Element = document.selectHead(".govuk-error-summary")
      errorSummary.selectHead("h2").text mustBe "There is a problem."
      val errorLink: Element = errorSummary.selectHead("div > ul > li > a")
      errorLink.text mustBe formError.message
      errorLink.attr("href") mustBe s"#${formError.key}"

      val errorMessage: Element = document.selectHead("form").selectHead(".govuk-error-message")
      errorMessage.text mustBe s"Error: ${formError.message}"
    }

  }

  implicit class ElementTest(element: Element) {

    def selectHead(selector: String): Element = {
      element.select(selector).asScala.headOption match {
        case Some(element) => element
        case None => fail(s"No elements returned for selector: $selector")
      }
    }

    def selectOptionally(selector: String): Option[Element] = {
      element.select(selector).asScala.headOption
    }

    def selectNth(selector: String, nth: Int): Element = {
      if (nth < 1) fail(s"Unable to select the $nth element, use a number >= 1")
      element.select(selector).asScala.lift(nth - 1) match {
        case Some(element) => element
        case None => fail(s"Could not retrieve $selector number $nth")
      }
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

    def getForm: Element = element.selectHead("form")

    def getSpan(id: String): Elements = element.select(s"""span[id=$id]""")

    def getLink(id: String): Elements = element.select(s"""a[id=$id]""")

    def getTextFieldInput(id: String): Elements = element.select(s"""input[id=$id]""")

    def getFieldErrorMessage(id: String): Elements = element.select(s"""a[id=$id-error-summary]""")

    def getBulletPointList: Elements = element.select("ul[class=list list-bullet]")

  }

  implicit class ElementTests(element: Element) {

    def mustHaveHeadingAndCaption(heading: String, caption: String, isSection: Boolean): Assertion = {
      val checkpoint: Checkpoint = new Checkpoint()

      checkpoint {
        element.selectHead("h1.govuk-heading-l").text mustBe heading
      }

      if (isSection) {
        checkpoint {
          element.selectHead(".govuk-caption-l").text mustBe s"This section is $caption"
        }
        checkpoint {
          element.selectHead(".govuk-caption-l").selectHead("span.govuk-visually-hidden").text mustBe "This section is"
        }
      } else {
        checkpoint {
          element.selectHead("span.govuk-caption-l").text mustBe caption
        }
      }

      checkpoint.reportAll()
      Succeeded
    }

    def mustHaveRadioInput(selector: String)(name: String,
                                             legend: String,
                                             isHeading: Boolean,
                                             isLegendHidden: Boolean,
                                             hint: Option[String],
                                             errorMessage: Option[String],
                                             radioContents: Seq[RadioItem],
                                             isInline: Boolean = false): Assertion = {

      val checkpoint: Checkpoint = new Checkpoint()
      val radioFieldSet: Element = element.selectHead(selector)

      validateFieldSetLegend(radioFieldSet, legend, isHeading, isLegendHidden, checkpoint)

      hint.foreach{ hint =>
        val radioFieldSetHint: Element = radioFieldSet.selectHead(".govuk-hint")
        checkpoint {
          radioFieldSet.attr("aria-describedby") must include(radioFieldSetHint.attr("id"))
        }
        checkpoint {
          radioFieldSetHint.text mustBe hint
        }
      }

      errorMessage.foreach{ errorMessage =>
        val radioFieldSetError: Element = radioFieldSet.selectHead(".govuk-error-message")
        checkpoint {
          radioFieldSet.attr("aria-describedby") must include(radioFieldSetError.attr("id"))
        }
        checkpoint {
          radioFieldSetError.text must include (errorMessage)
        }
      }

      val radioField: Element = if (isInline) element.selectHead(".govuk-radios--inline") else element.selectHead(".govuk-radios")

      radioContents.zipWithIndex foreach { case (radioContent, index) =>
        if (radioContent.divider.isDefined) {
          validateRadioDivider(radioField, radioContent, index, checkpoint)
        } else {
          validateRadioItem(radioField, name, radioContent, index, checkpoint)
        }
      }
      checkpoint.reportAll()
      Succeeded
    }

    private def validateFieldSetLegend(radioFieldSet: Element,
                                       legend: String,
                                       isHeading: Boolean,
                                       isLegendHidden: Boolean,
                                       checkpoint: Checkpoint): Unit = {
      val radioFieldSetLegend: Element = radioFieldSet.selectHead("legend")
      if (isHeading) {
        checkpoint {
          radioFieldSetLegend.getH1Element.text mustBe legend
        }
      } else {
        checkpoint {
          radioFieldSetLegend.text mustBe legend
        }
        if (isLegendHidden) {
          checkpoint {
            radioFieldSetLegend.attr("class") must include("govuk-visually-hidden")
          }
        } else {
          checkpoint {
            radioFieldSetLegend.attr("class") mustNot include("govuk-visually-hidden")
          }
        }
      }
    }

    private def validateRadioItem(radioField: Element, name: String, radioItem: RadioItem, index: Int, checkpoint: Checkpoint): Unit = {
      val radioItemElement: Element = radioField.child(index)
      val radioInput: Element = radioItemElement.selectHead("input")
      val radioLabel: Element = radioItemElement.selectHead("label")
      val radioInputId: String = if (index == 0) name else s"$name-${index + 1}"

      checkpoint {
        radioItemElement.className() mustBe "govuk-radios__item"
      }
      checkpoint {
        radioInput.attr("id") mustBe radioInputId
      }
      checkpoint {
        radioInput.attr("name") mustBe name
      }
      checkpoint {
        radioInput.attr("type") mustBe "radio"
      }
      checkpoint {
        radioInput.attr("value") mustBe radioItem.value.getOrElse("")
      }
      checkpoint {
        radioLabel.attr("for") mustBe radioInput.attr("id")
      }
      checkpoint {
        Text(radioLabel.text) mustBe radioItem.content
      }
      radioItem.hint.foreach { hint =>
        checkpoint {
          Text(radioItemElement.selectHead(".govuk-radios__hint").text) mustBe hint.content
        }
      }
    }

    private def validateRadioDivider(radioField: Element, radioDivider: RadioItem, index: Int, checkpoint: Checkpoint): Unit = {
      val dividerElement: Element = radioField.child(index)
      checkpoint {
        dividerElement.className() mustBe "govuk-radios__divider"
      }
      checkpoint {
        dividerElement.text() mustBe radioDivider.divider.get
      }
    }
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

  def method(method: String): HavePropertyMatcher[Element, String] =
    (element: Element) => HavePropertyMatchResult(
      element.attr("method") == method,
      "method",
      method,
      element.attr("method")
    )

  def action(action: String): HavePropertyMatcher[Element, String] =
    (element: Element) => HavePropertyMatchResult(
      element.attr("action") == action,
      "action",
      action,
      element.attr("action")
    )

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
          "(exact value not found)"
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
