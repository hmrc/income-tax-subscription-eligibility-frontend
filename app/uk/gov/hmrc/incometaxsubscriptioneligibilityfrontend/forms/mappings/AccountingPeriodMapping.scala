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

package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.forms.mappings

import play.api.data.Forms.of
import play.api.data.format.Formatter
import play.api.data.{FormError, Mapping}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.AccountingPeriod
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.AccountingPeriod._

object AccountingPeriodMapping {

  def accountingPeriodMapping(error: String): Mapping[AccountingPeriod] = {
    of(accountingPeriodFormatter(error))
  }

  private def accountingPeriodFormatter(error: String): Formatter[AccountingPeriod] = {
    new Formatter[AccountingPeriod] {
      override def bind(key: String, data: Map[String, String]): Either[Seq[FormError], AccountingPeriod] = {
        data.get(key) match {
          case Some(SixthAprilToFifthApril.key) => Right(SixthAprilToFifthApril)
          case Some(FirstAprilToThirtyFirstMarch.key) => Right(FirstAprilToThirtyFirstMarch)
          case Some(OtherAccountingPeriod.key) => Right(OtherAccountingPeriod)
          case _ => Left(Seq(FormError(key, error)))
        }
      }

      override def unbind(key: String, value: AccountingPeriod): Map[String, String] = {
        Map(key -> value.key)
      }
    }
  }

}
