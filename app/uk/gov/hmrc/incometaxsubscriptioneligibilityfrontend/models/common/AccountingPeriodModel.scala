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

package models.common

import models.DateModel
import play.api.libs.json.{Json, OFormat}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.subscription.AccountingPeriodUtil

import java.time.LocalDate


case class AccountingPeriodModel(startDate: DateModel, endDate: DateModel) {
  lazy val taxStartYear: Int = AccountingPeriodUtil.getTaxStartYear(this)
  lazy val taxEndYear: Int = AccountingPeriodUtil.getTaxEndYear(this)
  lazy val taxEndYearShort: Int = taxEndYear % 100
  lazy val adjustedTaxYear: AccountingPeriodModel =
    if (taxEndYear <= 2018) {
      val nextStartDate = this.endDate.toLocalDate.plusDays(1)
      val nextEndDate = nextStartDate.plusYears(1).minusDays(1)
      AccountingPeriodModel(DateModel.dateConvert(nextStartDate), DateModel.dateConvert(nextEndDate))
    }
    else this
  val toShortTaxYear: String = s"${startDate.year.takeRight(2)}-${endDate.year.takeRight(2)}"
}

object AccountingPeriodModel {
  def apply(updateFrom: LocalDate, updateTo: LocalDate): AccountingPeriodModel =
    new AccountingPeriodModel(DateModel.dateConvert(updateFrom), DateModel.dateConvert(updateTo))

  implicit val format: OFormat[AccountingPeriodModel] = Json.format[AccountingPeriodModel]
}
