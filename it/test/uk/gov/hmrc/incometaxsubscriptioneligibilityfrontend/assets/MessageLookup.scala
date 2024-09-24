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

import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.subscription.AccountingPeriodUtil

import java.time.format.DateTimeFormatter

object MessageLookup {
  val suffix = " - Use software to send Income Tax updates - GOV.UK"
  val agentSuffix = " - Use software to report your client’s Income Tax - GOV.UK"

  object Base {
    val continue = "Continue"
    val yes = "Yes"
    val no = "No"
    val acceptAndContinue = "Accept and continue"
    val backUrl = "Back"
  }

  object CannotSignUp {
    val heading = "You cannot sign up yet"
    val paraOne = "Your business does not use an accounting period that runs from either:"
    val bulletOne = "1 April to 31 March"
    val bulletTwo = "6 April to 5 April"
    val paraTwo = "Making Tax Digital for Income Tax is only available to people who use these business accounting periods."
    val paraThree = "In the future, we may extend this service to more people."
    val sendSelfAssessment = "Continue submitting your Self Assessment tax return as normal."
    val sendSelfAssessmentLink = "Self Assessment tax return"
    val sendSelfAssessmentHref = "https://www.gov.uk/self-assessment-tax-returns/sending-return"
  }

  object IndividualSignUpTerms {
    val heading = "Signing up for Making Tax Digital for Income Tax"
    val subheading = "How to sign up"

    object Heading {
      val paraOne: String = "Making Tax Digital for Income Tax is a new way of reporting income to HMRC. " +
        "It’s currently in a voluntary phase for selected self-employed businesses and landlords."
    }

    object beforeYouSignUp {
      val heading = "Before you sign up"
      val paraOne = "To sign up, you must be a sole trader or get income from either a UK or foreign property."
      val paraTwoLinkText = "software that’s compatible with Making Tax Digital for Income Tax (opens in new tab)."
      val paraTwo = s"You must also use $paraTwoLinkText"
    }

    object soleTrader {
      val heading = "Sole trader"
      val paraOne = "You’re a sole trader if you run your own business as an individual and work for yourself. This is also known as being self-employed."
      val paraTwo = "You’re not a sole trader if your only business income is from a limited company."
    }

    object incomeProperty {
      val heading = "Income from property"
      val paraOne = "You can sign up if you get income from property in the UK or from property in another country. For example, letting houses, flats or holiday homes either on a long or short term basis."
    }

  }

  object AgentSignupTerms {

    object Heading {
      val heading = "Signing up your clients for Making Tax Digital for Income Tax"
    }

    object BeforeSignUp {
      val heading = "Before you sign up your clients"
      val paraOne = "To sign up your clients, you must have their authorisation in your agent services account."
      val paraTwoLinkText = "software that’s compatible with Making Tax Digital for Income Tax (opens in new tab)"
      val paraTwo = s"Make sure you or your clients use $paraTwoLinkText."
      val paraThree = "Make sure your client is a sole trader or gets income from property (inside or outside the UK)."
    }

    object AccountingPeriod {
      val heading = "Accounting period"
      val paraOne = "Make sure your client uses either:"
      val bulletOne = "an accounting period that runs from 6 April to 5 April"
      val bulletTwo = "an accounting period that runs from 1 April to 31 March (and their compatible software supports calendar update periods)"
    }

    object CheckSignUp {
      val heading = "We will check if you can sign up each client"
      val paraOne = "When you continue, you’ll need to enter these details for one of your clients:"
      val bulletOne = "name"
      val bulletTwo = "National Insurance number"
      val bulletThree = "date of birth"
      val paraTwo = "We’ll check that client’s record and tell you if you can sign them up."
    }
  }

  object CheckAccountingPeriod {
    val heading = "What accounting period do you use for your business?"
    val paraOne = "This is the accounting period you use each year when reporting your business income and expenses to HMRC."
    val paraTwo = "Your accounting period runs from the date your books or accounts start. It ends on the date your books or accounts are made up to."
    val hint = "For example, your business accounting period might start on 6 April and end on 5 April of the following year (such as, 6 April 2026 to 5 April 2027)."
    val sixthToFifth = "6 April to 5 April"
    val sixthToFifthHint = "Your books or accounts start on 6 April and are made up to 5 April of the following year (such as, 6 April 2026 to 5 April 2027)"
    val firstToThirtyFirst = "1 April to 31 March"
    val firstToThirtyFirstHint = "Your books or accounts start on 1 April and are made up to 31 March of the following year (such as, 1 April 2026 to 31 March 2027)"
    val or = "or"
    val neither = "Neither of these"
    val caption = "Eligibility questions"
    val continue = "Continue"
    val error = "Select if your accounting period runs from 6 April to 5 April, 1 April to 31 March, or neither of these"
  }

  object SignUpToPilot {
    val heading = "Continue your sign up for Making Tax Digital for Income Tax"
    val paragraph = "Based on your answers, you can continue to sign up for Making Tax Digital for Income Tax."
  }
}
