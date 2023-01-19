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

package models.subscription

import models.DateModel
import org.scalatest.OptionValues
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.subscription.AccountingPeriodUtil


class AccountingPeriodUtilSpec extends AnyWordSpecLike with Matchers with OptionValues {

  "AccountingPeriodUtil.getCurrentTaxYearStartDate" should {
    "return the start date for the current tax year" in {
      AccountingPeriodUtil.getCurrentTaxYearStartDate shouldBe DateModel("6", "4", (AccountingPeriodUtil.getCurrentTaxEndYear - 1).toString)
    }
  }

  "AccountingPeriodUtil.getCurrentTaxYearEndDate" should {
    "return the end date for the current tax year" in {
      AccountingPeriodUtil.getCurrentTaxYearEndDate shouldBe DateModel("5", "4", AccountingPeriodUtil.getCurrentTaxEndYear.toString)
    }
  }

  "AccountingPeriodUtil.getCurrentTaxYearRange" should {
    "return the year range for the current tax year" in {
      val startString = AccountingPeriodUtil.getCurrentTaxYear.startDate.year
      val endString = AccountingPeriodUtil.getCurrentTaxYear.endDate.year.takeRight(2)
      AccountingPeriodUtil.getCurrentTaxYearRange shouldBe s"$startString-$endString"
    }
  }

}
