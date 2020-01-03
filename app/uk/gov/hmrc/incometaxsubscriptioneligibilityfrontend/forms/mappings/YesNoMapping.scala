/*
 * Copyright 2020 HM Revenue & Customs
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

import play.api.data.FormError
import play.api.data.format.Formatter
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.{No, Yes, YesNo}

object YesNoMapping {

  val option_yes: String = "yes"
  val option_no: String = "no"

  def yesNoMapping(error: String): Formatter[YesNo] = new Formatter[YesNo] {
    override def bind(key: String, data: Map[String, String]): Either[Seq[FormError], YesNo] = {
      data.get(key) match {
        case Some(`option_yes`) => Right(Yes)
        case Some(`option_no`) => Right(No)
        case _ => Left(Seq(FormError(key, error)))
      }
    }

    override def unbind(key: String, value: YesNo): Map[String, String] = {
      val stringValue = value match {
        case Yes => option_yes
        case No => option_no
      }

      Map(key -> stringValue)
    }
  }
}
