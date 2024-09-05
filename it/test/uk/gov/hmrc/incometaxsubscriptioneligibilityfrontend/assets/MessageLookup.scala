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
      val haveIncomeTypePara = "You can sign up your client for Making Tax Digital for Income Tax if they:"
      val haveIncomeTypeBullet1 = "are a sole trader"
      val haveIncomeTypeBullet2 = "receive income from either a UK or foreign property"
      val accountingPeriodPara = "They must also use either:"
      val accountingPeriodBullet1 = "an accounting period that runs from 6 April to 5 April"
      val accountingPeriodBullet2 = "accountancy software that supports calendar quarterly periods and their accounting period runs from 1 April to 31 March"
    }

    object HowToSignUp {

      val heading = "How to sign up"

      object GetReady {
        val heading = "Get ready to sign up"
        val mustHavePara = "To sign up your clients, you must have:"
        val mustHaveBullet1 = "an agent services account"
        val mustHaveBullet2 = "software that’s compatible with Making Tax Digital for Income Tax"
        val clientSoftwarePara = "You should also make sure your clients use software that’s compatible with Making Tax Digital for Income Tax."

        val eachClientPara = "To sign up each client you’ll need their:"
        val eachClientName = "name"
        val eachClientNino = "National Insurance number"
        val eachClientDOB = "date of birth"
        val eachClientBusinessDetails1 = "business trading name and address"
        val eachClientBusinessDetails2 = "the date their business started trading"
        val eachClientBusinessDetails3 = "their trade"
        val eachClientAuthorisation = "Your client will need to authorise you to sign them up."
      }

      object CheckEligibility {
        val heading = "Check your client can sign up"
        val para = "We’ll check if your client can sign up when you start to sign them up."
        val cantSignUp = "If they cannot sign up, you must continue submitting their Self Assessment tax returns as normal."
      }

      object CompleteSignUp {
        val heading = "Complete sign-up tasks"
        val para = "To sign up your client you need to:"
        val bullet1 = "add any sole trader or UK and foreign property business income"
        val bullet2 = "select to start using Making Tax Digital for Income Tax either this tax year or next tax year"
      }

      object ConfirmSignUp {
        val heading = "Confirm sign up"
        val para1 = "We’ll then confirm your client has been signed up."
        val para2 = "You’ll be asked if you would like to sign up another client."
      }
    }
  }

  object CheckAccountingPeriod {
    val heading = "What accounting period do you use for your business?"
    val para = "This is the accounting period you use each year when reporting your business income and expenses to HMRC."
    val hint = "For example, your business accounting period might start on 6 April and end on 5 April of the following year (such as, 6 April 2026 to 5 April 2027)."
    val sixthToFifth = "6 April to 5 April"
    val firstToThirtyFirst = "1 April to 31 March"
    val or = "or"
    val neither = "Neither of these"
    val caption = "This section is Eligibility questions"
    val continue = "Continue"
    val error = "Select your business accounting period, or select ‘Neither of these’"
  }

  object SignUpToPilot {
    val heading = "Continue your sign up for Making Tax Digital for Income Tax"
    val paragraph = "Based on your answers, you can continue to sign up for Making Tax Digital for Income Tax."
  }
}
