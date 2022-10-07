/*
 * Copyright 2022 HM Revenue & Customs
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

import org.scalatest.matchers.{MatchResult, Matcher}
import play.api.Logger

trait MessagesMatcher {
  val excludedKeys: Set[String] = Set.empty

  private val messagesMatcherLogger: Logger = Logger(getClass)

  def allBeIn(messages: Set[String]): MessagesMatcher = MessagesMatcher(messages)

  def containUniqueKeys: DuplicateMatcher = DuplicateMatcher()

  case class MessagesMatcher(messages: Set[String]) extends Matcher[Set[String]] {
    override def apply(left: Set[String]): MatchResult = {
      if(excludedKeys.nonEmpty) {
        messagesMatcherLogger.warn(s"\n ---- WARNING: ${excludedKeys.size} translation key(s) excluded from the test ---- \n  ${excludedKeys.mkString("\n  ")}")
      }

      val diff = left -- messages -- excludedKeys
      MatchResult(
        diff.isEmpty,
        s"Missing ${diff.size} translation(s):\n  ${diff.mkString("\n  ")}",
        ""
      )
    }
  }

  case class DuplicateMatcher() extends Matcher[List[String]] {
    override def apply(left: List[String]): MatchResult = {
      val diff = left.diff(left.distinct).distinct
      MatchResult(
        diff.isEmpty,
        s"${diff.size} duplicate key(s):${diff.mkString("\n  ","\n  ", "\n")}",
        ""
      )
    }
  }
}
