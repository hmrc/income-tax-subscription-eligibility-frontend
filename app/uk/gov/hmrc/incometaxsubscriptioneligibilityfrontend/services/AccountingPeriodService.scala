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

package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.services

import java.time.LocalDate
import java.time.Month.APRIL
import javax.inject.Singleton

@Singleton
class AccountingPeriodService {
  private val SIXTH = 6

  def currentTaxYear: String = {
    val currentTaxEndYear = getTaxEndYear(LocalDate.now())
    s"${currentTaxEndYear - 1}-${currentTaxEndYear.toString.slice(2, 4)}"
  }

  def nextTaxYear: String = {
    val currentTaxEndYear = getTaxEndYear(LocalDate.now())
    s"${currentTaxEndYear}-${(currentTaxEndYear + 1).toString.slice(2, 4)}"
  }

  private def getTaxEndYear(date: LocalDate): Int =
    if (date.isBefore(LocalDate.of(date.getYear, APRIL.getValue, SIXTH))) {
      date.getYear
    } else {
      date.getYear + 1
    }
}
