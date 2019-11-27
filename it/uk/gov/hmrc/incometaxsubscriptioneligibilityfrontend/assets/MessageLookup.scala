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
  val suffix = " - Use software to send Income Tax updates - GOV.UK"
  val agentSuffix = " - Use software to report your client's Income Tax - GOV.UK"

  object Overview {
    val heading: String = "The way you record Self Assessment is changing"
    val title = s"$heading$suffix"
    val line1 = "Making Tax Digital for Income Tax is a new way for self-employed businesses (sole traders) and landlords to send income tax updates to HMRC."
    val line2 = "You can use software to keep your business records digitally and send income tax updates to HMRC, instead of filing a Self Assessment tax return."
    val linkText = "Find out more about Making Tax Digital for income tax (opens in a new tab)"
  }

  object Base {
    val continue = "Continue"
    val yes = "Yes"
    val no = "No"
    val acceptAndContinue = "Accept and continue"
  }

  object HaveAnyOtherIncome {
    val title = s"Do you have any sources of income other than self employment or property income?"
    val include = "This could include:"
    val notInclude = "This does not include:"
    val includePoints: Seq[String] =
      Seq("as an employee for another business", "UK pensions or annuities", "investments from outside the UK",
        "capital gains", "taxable state benefits")
    val notIncludePoints: Seq[String] = Seq("bank and building society interest", "dividends")
    val error = "Select yes if you have any sources of income other than self employment or property income"
  }

  object HaveAnyOtherIncomeError {
    val title = "You cannot use this service yet"
    val line1 = "This is a trial service. You may be able to use the 'Use software to send Income Tax updates' service in the future."
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
  }

  object GetSoftware {
    val header = "You will need to get compatible software after signing up"
    val title = s"$header$suffix"
    val line1 = "The software you use must be able to send income and expense reports to HMRC."
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

  object CheckCompatibleSoftware {
    val heading = "Check that the software you use is compatible with Making Tax Digital for Income Tax"
    val title = s"$heading$suffix"
    val line = "You must check that your software can connect and send income and expense reports to HMRC."
    val link = "Check if your software is compatible (opens in a new tab)"
    val linkHref = "https://www.gov.uk/guidance/find-software-thats-compatible-with-making-tax-digital-for-income-tax"
  }

  object AgentTerms {
    val heading: String = "Terms of participation"
    val title = s"$heading$agentSuffix"
    val line1 = "By taking part in this pilot, you agree that either you or your client will:"
    val bullet1 = "use relevant software to record income and expenses"
    val bullet2 = "provide an email address so your client can be contacted by HMRC"
    val bullet3 = "submit a quarterly report from the start of your client's accounting period"
    val bullet4 = "send your client's final report by the 31 January at the end of their tax year"
    val bullet5 = "tell HMRC if your client stops trading or starts a new business"
    val bullet6 = "submit reports using your usual method if your client leaves this pilot"
    val line2 = "These terms are not contractual and your client can stop taking part in this pilot at any time."
  }

  object OtherSourcesOfIncomeError {
    val title = s"Your client can't use this service$agentSuffix"
    val heading = "You cannot use this service"
    val para1 = "You can only report Income Tax through software for clients that either:"
    val bullet1 = "have a sole trader business"
    val bullet2 = "rent out a UK property"
    val bullet3 = "have a sole trader business and rent out a UK property"
    val para2 = "If your client has other sources of income, they need to send a Self Assessment tax return instead."
    val linkText = "send a Self Assessment tax return"
  }
}
