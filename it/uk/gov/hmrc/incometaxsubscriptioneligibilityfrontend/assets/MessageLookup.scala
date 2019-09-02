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

package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.assets

object MessageLookup {
  val suffix = " - Report your income and expenses quarterly - GOV.UK"

  object Overview {
    val heading: String = "The way you record Self Assessment is changing"
    val title = s"$heading$suffix"
    val line1 = "Making Tax Digital for Income Tax is a new way for self-employed businesses (sole traders) and landlords to send income tax updates to HMRC."
    val line2 = "You can use software to keep your business records digitally and send income tax updates to HMRC, instead of filing a Self Assessment tax return."
    val linkText = "Find out more about Making Tax Digital for income tax (opens in a new tab)"
    val button = "Continue"
  }

  object Terms {
    val heading: String = "Terms of participation"
    val title = s"$heading$suffix"
    val line1 = "By taking part in this pilot, you agree that you will:"
    val bullet1 = "use relevant software to record income and expenses"
    val bullet2 = "provide an email address so your client can be contacted by HMRC"
    val bullet3 = "submit a quarterly report from the start of your accounting period"
    val bullet4 = "send your final report by the 31 January at the end of their tax year"
    val bullet5 = "declare any other income sources and reliefs you have"
    val bullet6 = "tell HMRC if you stop trading or start a new business"
    val bullet7 = "submit reports using your usual method if you leave this pilot"
    val line2 = "You can stop taking part in this pilot at any time."
    val button = "Accept and continue"
  }

  object GetSoftware {
    val header = "You will need to get compatible software after signing up"
    val title = s"$header$suffix"
    val line1 = "The software you use must be able to send income and expense reports to HMRC."
    val continue = "Continue"
    val findSoftware = "Find software that's compatible with Making Tax Digital for Income Tax (opens in a new tab)"
    val findSoftwareLink = "https://www.gov.uk/guidance/find-software-thats-compatible-with-making-tax-digital-for-income-tax"
  }

  object HaveYouGotSoftware {
    val heading = "Do you currently use accounting software?"
    val title = s"$heading$suffix"
    val hint = "This does not include any spreadsheet software."
    val yes = "Yes"
    val no = "No"
    val continue = "Continue"
  }

}
