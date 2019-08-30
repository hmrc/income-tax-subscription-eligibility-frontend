/*
 * Copyright 2019 HM Revenue & Customs
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

package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.views

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.scalatest.matchers.{HavePropertyMatchResult, HavePropertyMatcher}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.ws.WSResponse
import play.api.test.Helpers._

import scala.concurrent.Future

trait ViewSpec extends PlaySpec with GuiceOneAppPerSuite {
  playSpec: PlaySpec =>

  implicit class DocumentTest(doc: Document) {

    val getParagraphAsText: String = doc.getElementsByTag("p").text()

    val getBulletPointsAsText: String = doc.getElementsByTag("li").text()

    val getH1ElementAsText: String = doc.getElementsByTag("h1").text()

    val getH2ElementAsText: String = doc.getElementsByTag("h2").text()

    val getFirstH2ElementAsText: String = doc.getElementsByTag("h2").first().text()

    val getFormElements: Elements = doc.getElementsByClass("form-field-group")

    val getErrorSummaryMessage: String = doc.select("#error-summary-display ul").text()

    val getButtonContentAsText: String = doc.select("button[type=submit]").text()

    val getHintAsText: String = doc.select(s"""span[class=form-hint]""").text()

    val getFieldErrorMessageHeading: String = doc.select("#error-summary-heading").text()

    def getSpanAsText: String = doc.select("span").text()

    def getSpanAsText(id: String): String = doc.select(s"""span[id=$id]""").text()

    def getALinkText(id: String): String = doc.select(s"""a[id=$id]""").text()

    def getLinkHrefAsText(id: String): String = doc.select(s"""a[id=$id]""").attr("href")

    def getLinkTextFromHref(href: String): String = doc.select(s"""a[href=$href]""").text()

    def hasTextFieldInput(name: String): Boolean = doc.select(s"input[id=$name]").hasAttr("name")

    def getTextFieldInput(name: String): Elements = doc.select(s"""input[name=$name]""")

    def getTextFieldLabel(name: String): String = doc.select(s"label[for=$name]").select("span").text()

    def getFieldErrorMessageContent(fieldName: String): String = doc.select(s"""a[id=$fieldName-error-summary]""").text()

    def paras: Elements = doc.select("p")

    def bulletPointList: Elements = doc.select("ul[class=list list-bullet]")

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
        val errorMessage = element.parents.first.select("span[class=error-notification")

        HavePropertyMatchResult(
          errorMessage.text() == message,
          "input error message",
          message,
          errorMessage.text()
        )
      }
    }

  def getDocFromResponse(response: Future[WSResponse]): Document =
    Jsoup.parse(await(response).body)

}