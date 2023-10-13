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

  object Overview {
    val heading: String = "The way you complete Self Assessment is changing"
    val line1 = "This is a pilot to sign up for a new way for sole trader businesses (self-employed) and landlords to send income tax updates and returns to HMRC."
    val line2 = "You will need to use compatible software to:"
    val line3 = "This will replace filing a Self Assessment tax return and you can choose your software once you have signed up. Find software that’s compatible with Making Tax Digital for Income Tax (opens in new tab)"
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
    val caption = "This section is Check you can sign up - question 1"
    val include = "This could include:"
    val notInclude = "This does not include:"
    val includePoints: Seq[String] =
      Seq("PAYE income as an employee", "UK pensions or annuities", "investments from outside the UK",
        "capital gains", "taxable state benefits")
    val notIncludePoints: Seq[String] = Seq("bank and building society interest", "dividends")
    val error = "Select yes if you have any sources of income other than self employment or property income."
  }

  object PropertyStartAfter {
    def title(date: String) = s"Did you start renting out a property on or after $date?"

    val hintMessage1 = "This includes being a landlord or using a letting agency for:"
    val hintMessage2 = "UK properties"
    val hintMessage3 = "overseas properties"
    val hintMessage4 = "holiday properties"
    val hintMessage5 = "This does not include renting out:"
    val hintMessage6 = "a room"
    val hintMessage7 = "part of your property"

    val caption = "This section is Check you can sign up - question 3"

    def error(date: String) = s"Select yes if you own a property business that began trading on or after $date."
  }

  object SoleTraderStartAfter {
    def title(date: String) = s"Did your sole trader business begin on or after $date?"

    val caption = "This section is Check you can sign up - question 2"

    def error(date: String) = s"Select yes if you are a sole trader that began trading on or after $date."

    val hintMessage = "If you’re a sole trader, you run your own business as an individual and are self-employed (opens in new tab)"
  }

  object CannotSignUp {
    val heading = "You cannot take part yet"
    val incomePara = "Making Tax Digital for Income Tax is not currently available right now to users who have certain types of income or who have not been trading long enough. You will not be able to take part in this voluntary phase if you receive income from:"
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

  object WhatYouNeedToDo {
    val heading: String = "What you need to do"
    val title = s"$heading$suffix"
    val paraOne = "You can choose to stop using Making Tax Digital for Income Tax at any time until 6 April 2026. You do not have to let us know and you can ignore any secure message from the service. But you must file your Self Assessment tax return at the end of the year as normal."
    val yearOnePara = "You must meet the Making Tax Digital for Income Tax requirements for 6 April 2026, if all of the following apply:"
    val yearOneBulletOneLinkText = "find out more about registering and sending a Self Assessment tax return (opens in new tab)"
    val yearOneBulletOne = s"you are registered for Self Assessment - $yearOneBulletOneLinkText"
    val yearOneBulletTwo = "you get income from self-employment or property, or both"
    val yearOneBulletThree = "your total qualifying income is more than £50,000"
    val yearTwoPara = "You must meet the Making Tax Digital for Income Tax requirements for 6 April 2027, if all of the following apply:"
    val yearTwoBulletOne = "you are registered for Self Assessment"
    val yearTwoBulletTwo = "you get income from self-employment or property, or both"
    val yearTwoBulletThree = "your total qualifying income is more than £30,000"
    val paraTwo = "You can stop using Making Tax Digital for Income Tax when the pilot ends on 5 April 2026 if your income is less than £50,000."
  }

 object IndividualSignUpTerms {
   val heading = "Signing up for Making Tax Digital for Income Tax"
   val subheading = "How to sign up"

   object Heading {
     val paraOne = "Making Tax Digital for Income Tax is a new way of reporting income to HMRC. " +
       "It’s currently in a voluntary phase for selected self-employed businesses and landlords."

     object Inset {
       val paraOne = "Signing up for Making Tax Digital for Income Tax is voluntary at the moment. However, if your total qualifying income is more than:"
       val bulletOne = "£50,000 or more for April 2026"
       val bulletTwo = "£30,000 or more for April 2027"
       val paraTwo = "You can still choose to voluntarily sign up."
     }

     val paraTwo = "You may be able to sign up for Making Tax Digital for Income Tax if you:"
     val bulletOne = "are a sole trader"
     val bulletTwo = "receive income from property"
   }

   object CheckEligibility {
     val heading = "Check your eligibility"
     val paragraph = "Answer the questions to find out if you may be eligible."
   }

   object HMRCAccount {
     val heading = "Sign in to your HMRC account"
     val paragraph1 = "If you find out you may be eligible, sign in to your HMRC account."
     val paragraph2 = "Depending on your circumstances, you will be offered to sign up for the:"
     val currentTaxYear = AccountingPeriodUtil.getCurrentTaxYearStartDate.toLocalDate.format(DateTimeFormatter.ofPattern("d MMMM YYYY"))
     val nextTaxYear = AccountingPeriodUtil.getCurrentTaxYearStartDate.toLocalDate.plusYears(1).format(DateTimeFormatter.ofPattern("d MMMM YYYY"))
     val currentTaxYearEnd = AccountingPeriodUtil.getCurrentTaxYearEndDate.toLocalDate.format(DateTimeFormatter.ofPattern("d MMMM YYYY"))
     val nextTaxYearEnd = AccountingPeriodUtil.getCurrentTaxYearEndDate.toLocalDate.plusYears(1).format(DateTimeFormatter.ofPattern("d MMMM YYYY"))
     val bullet1 = s"current tax year (${currentTaxYear} to ${currentTaxYearEnd})"
     val bullet2 = s"next tax year (${nextTaxYear} to ${nextTaxYearEnd})"
     val insetPara = "There may be circumstances which stop you from using Making Tax Digital for Income Tax at the moment. " +
       "These may include certain other incomes or activities, or the length of time you’ve been self-employed."
     val afterInsetPara = "If you’re not eligible for Making Tax Digital for Income Tax yet, " +
       "continue to submit your annual Self Assessment as normal."
   }

   object CompleteSignUp {
     val heading = "Complete sign-up tasks"
     val paragraph1 = "To sign up you need to:"
     val bullet1 = "confirm the details we have about you and your business are correct"
     val bullet2 = "add any other income sources"
     val bullet3 = "select which year you want to start using Making Tax Digital for Income Tax"
   }

   object SignUp {
     val heading = "Sign up"
     val paragraph = "You’ll be told you’ve successfully signed up for Making Tax Digital for Income Tax."
   }
 }

  object AgentSignupTerms {
    object AgentHeading {
      val heading = "Signing up your clients up for Making Tax Digital for Income Tax"
      val para1 = "Making Tax Digital for Income Tax is a new way of reporting income to HMRC. It’s currently in a voluntary phase for selected self-employed businesses and landlords."
      val para2 = "You may be able to sign your client up for Making Tax Digital for Income Tax if they:"
      val bullet1 = "are a sole trader"
      val bullet2 = "receive income from property"
      val para3 = "Self-employed people trading through limited companies cannot sign-up for Making Tax Digital for Income Tax."
    }

    object AgentHowToSignUp {
      object Heading {
        val heading = "How to sign up"
      }
      object AgentGetReady {
        val heading = "Get your clients ready"
        val para = "Make a list of clients you think may be eligible for Making Tax Digital for Income Tax. If you think they’re eligible and would benefit from joining the voluntary phase, check if they’d like to take part. Your client will need to authorise you to sign them up."
      }
      object AgentCheckAvailability {
        val heading = "Check your client is eligible"
        val para = "Answer the survey to check if your client is eligible."
      }
      object AgentGetInformation {
        val heading = "Prepare your client’s information"
        val para = "Make sure you’ve got the necessary information to sign up your client. This includes their:"
        val bullet1 = "name"
        val bullet2 = "National Insurance number"
        val bullet3 = "date of birth"
        val bullet4 = "the date their business started trading"
      }
      object AgentConfirmClientDetails {
        val heading = "Confirm client details"
        val para1 = "If you find out your client may be eligible, sign in to your HMRC agent account."
        val para2 = "Depending on their circumstances, you will be offered to sign them up for the:"
        val currentTaxYear = AccountingPeriodUtil.getCurrentTaxYearStartDate.toLocalDate.format(DateTimeFormatter.ofPattern("d MMMM YYYY"))
        val nextTaxYear = AccountingPeriodUtil.getCurrentTaxYearStartDate.toLocalDate.plusYears(1).format(DateTimeFormatter.ofPattern("d MMMM YYYY"))
        val currentTaxYearEnd = AccountingPeriodUtil.getCurrentTaxYearEndDate.toLocalDate.format(DateTimeFormatter.ofPattern("d MMMM YYYY"))
        val nextTaxYearEnd = AccountingPeriodUtil.getCurrentTaxYearEndDate.toLocalDate.plusYears(1).format(DateTimeFormatter.ofPattern("d MMMM YYYY"))
        val bullet1 = s"current tax year (${currentTaxYear} to ${currentTaxYearEnd})"
        val bullet2 = s"next tax year (${nextTaxYear} to ${nextTaxYearEnd})"
        val para3 = "There may be circumstances which stop your client from using Making Tax Digital for Income Tax right now. These may include certain other incomes or activities, or the length of time they’ve been self-employed."
        val para4 = "If they’re not eligible for Making Tax Digital for Income Tax yet, they should continue to declare their earnings through Self Assessment as usual."
      }
      object AgentCompleteSignUp {
        val heading = "Complete sign-up tasks"
        val para = "To sign up your client you need to:"
        val bullet1 = "confirm the details we have about your client and their business are correct"
        val bullet2 = "add any other income sources"
        val bullet3 = "select which year you want to start using Making Tax Digital for Income Tax"

      }
      object AgentConfirm {
        val heading = "Confirm sign-up"
        val para = "We’ll confirm your client has been signed up. You’ll also be offered to sign up another client."

      }
    }
  }


  object AgentTerms {
    val heading: String = "What you need to do"
    val paragraph1 = "By taking part you agree that you will:"
    val bullet1 = "use compatible software to record your client’s income and expenses"
    val bullet2 = "send quarterly updates from the start of their accounting period"
    val bullet3 = "submit their final declaration by 31 January following their current tax year"
    val bullet4 = "tell HMRC if they stop trading or start a new business"
    val subheading = "Your client can stop using this pilot at any time"
    val paragraph2 = "Your client can choose to stop using Making Tax Digital for Income Tax at any time until 6 April 2026. " +
      "You do not have to let us know and they can ignore any secure messages from the service. " +
      "But they must file their Self Assessment by 31 January following the end of the tax year as normal."
    val paragraph3 = "Your client must meet the Making Tax Digital for Income Tax requirements for 6 April 2026, if all of the following apply:"
    val bullet5 = "they are registered for Self Assessment ( find out more about registering and sending a Self Assessment tax return (opens in new tab))"
    val bullet6 = "they get income from self-employment or property, or both"
    val bullet7 = "their total qualifying income (opens in new tab) is more than £50,000"
    val paragraph4 = "Your client must meet the Making Tax Digital for Income Tax requirements for 6 April 2027, if all of the following apply:"
    val bullet8 = "they are registered for Self Assessment"
    val bullet9 = "they get income from self-employment or property, or both"
    val bullet10 = "their total qualifying income (opens in new tab) is more than £30,000"
    val paragraph5 = "Your client can stop using Making Tax Digital for Income Tax after the pilot ends on 5 April 2026, " +
      "if their qualifying income is less than £50,000."

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
    val caption = "This section is Check you can sign up - question 4"
    val yes = "Yes"
    val no = "No"
    val continue = "Continue"
    val error = "Select yes if all of your business accounting periods are from 6 April to 5 April."
  }

  object SignUpToPilot {
    val heading = "Continue your sign up for Making Tax Digital for Income Tax"
    val paragraph = "Based on your answers, you can continue to sign up for Making Tax Digital for Income Tax."
  }
}
