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
  val agentSuffix = " - Use software to report your client’s Income Tax - GOV.UK"

  object Overview {
    val heading: String = "The way you complete Self Assessment is changing"
    val line1 = "This is a pilot to sign up for a new way for sole trader businesses (self-employed) and landlords to send income tax updates and returns to HMRC."
    val line2 = "You will need to use compatible software to:"
    val line3 = "This will replace filing a Self Assessment tax return and you can choose your software once you have signed up. Find software that’s compatible with Making Tax Digital for Income Tax (opens in a new tab)"
    val bulletCompatibleSoftware1 = "keep your business records up to date"
    val bulletCompatibleSoftware2 = "send updates about your business records to HMRC every quarter"
    val bulletCompatibleSoftware3 = "file your Self Assessment tax return"
    val linkText = "Find software that’s compatible with Making Tax Digital for Income Tax (opens in new tab)."
    val linkHref = "https://www.gov.uk/guidance/sign-up-your-business-for-making-tax-digital-for-income-tax"
    val heading2 = "Before you start"
    val line4 = "To sign up to this pilot, you will need your:"
    val insetText = "You will need to use the same Government Gateway user ID and password that you use for your current Self Assessment account. If you do not have a user ID yet, you can create one when you use the service."
    val bulletBeforeStart1 = "Government Gateway user ID and password"
    val bulletBeforeStart2 = "National Insurance number"
    val bulletBeforeStart3 = "business start dates and registered addresses"
    val bulletBeforeStart4 = "business accounting year information"
  }

  object Base {
    val continue = "Continue"
    val yes = "Yes"
    val no = "No"
    val acceptAndContinue = "Accept and continue"
    val backUrl = "Back"
  }

  object HaveAnyOtherIncome {
    val title = "Apart from self-employment or property, do you have any other sources of income?"
    val caption = "This section is Check you can sign up to the pilot - question 1"
    val include = "This could include:"
    val notInclude = "This does not include:"
    val includePoints: Seq[String] =
      Seq("PAYE income as an employee", "UK pensions or annuities", "investments from outside the UK",
        "capital gains", "taxable state benefits")
    val notIncludePoints: Seq[String] = Seq("bank and building society interest", "dividends")
    val error = "Select yes if you have any sources of income other than self employment or property income."
  }

  object PropertyStartAfter {
    def title(date: String) = s"Did you start a property business on or after $date?"

    val hintMessage = "This includes being a landlord and letting holiday properties in the UK and overseas."

    val caption = "This section is Check you can sign up to the pilot - question 3"

    def error(date: String) = s"Select yes if you own a property business that began trading on or after $date."
  }

  object SoleTraderStartAfter {
    def title(date: String) = s"Did your sole trader business begin on or after $date?"

    val caption = "This section is Check you can sign up to the pilot - question 2"

    def error(date: String) = s"Select yes if you are a sole trader that began trading on or after $date."

    val hintMessage = "If you’re a sole trader, you run your own business as an individual and are self-employed (opens in a new tab)"
  }

  object CannotSignUp {
    val heading = "You cannot take part in this pilot yet"
    val incomePara = "This pilot is not available right now to users who have certain types of income or who have not been trading long enough. You will not be able to take part in this pilot if you receive income from:"
    val incomeBullet1 = "PAYE income as an employee"
    val incomeBullet2 = "UK pensions or annuities"
    val incomeBullet3 = "investments from outside the UK"
    val incomeBullet4 = "capital gains"
    val incomeBullet5 = "taxable state benefits"
    val otherPara = "You also cannot take part if your:"
    val otherBullet1 = "sole trader business began within the last two years"
    val otherBullet2 = "property business began within the last year"
    val otherBullet3 = "accounting period does not align with the standard tax year"
    val sendSelfAssessment = "You will need to send a Self Assessment tax return instead and you may be able to sign up in future."
    val sendSelfAssessmentLink = "Self Assessment tax return"
    val sendSelfAssessmentHref = "https://www.gov.uk/self-assessment-tax-returns/sending-return"
  }

  object Terms {
    val heading: String = "Terms of participation"
    val title = s"$heading$suffix"
    val line1 = "By taking part in this pilot, you agree that you will:"
    val bullet1 = "use compatible software to record income and expenses"
    val bullet2 = "provide an email address so you can be contacted by HMRC"
    val bullet3 = "send quarterly updates from the start of your accounting period"
    val bullet4 = "submit your final declaration by 31 January at the end of the tax year"
    val bullet5 = "tell HMRC if you stop trading or start a new business"
    val bullet6 = "use your usual Self Assessment method if you leave this pilot"
    val line2 = "You can leave this pilot at any time."
  }


  object AgentTerms {
    val heading: String = "Terms of participation"
    val line1 = "By taking part in this pilot, you agree that either you or your client will:"
    val bullet1 = "use relevant software to record income and expenses"
    val bullet2 = "send a quarterly update from the start of your client’s accounting period"
    val bullet3 = "submit your client’s final declaration by the 31 January at the end of their tax year"
    val bullet4 = "tell HMRC if your client stops trading or starts a new business"
    val bullet5 = "use your usual Self Assessment method if your client leaves this pilot"
    val line2 = "You or your client can leave this pilot at any time."
  }

  object OtherSourcesOfIncomeError {
    val title = s"Your client can’t use this service$agentSuffix"
    val heading = "You cannot use this service"
    val para1 = "You can only report Income Tax through software for clients that either:"
    val bullet1 = "have a sole trader business"
    val bullet2 = "rent out a UK property"
    val bullet3 = "have a sole trader business and rent out a UK property"
    val para2 = "If your client has other sources of income, they need to send a Self Assessment tax return instead."
    val linkText = "send a Self Assessment tax return"
  }

  object CheckAccountingPeriod {
    val heading = "Do all of your business accounting periods run from 6 April to 5 April?"
    val hint = "The tax year runs from 6 April to 5 April. Your accounting period for your self-employment or property needs to be the same if you would like to sign up to this service."
    val caption = "This section is Check you can sign up to the pilot - question 4"
    val yes = "Yes"
    val no = "No"
    val continue = "Continue"
    val error = "Select yes if all of your business accounting periods are from 6 April to 5 April."
  }

  object SignUpToPilot {
    val heading = "Continue your sign up to the pilot"
    val paragraph = "Based on your answers, you can continue to sign up to the pilot for Making Tax Digital for Income Tax."
  }
}
