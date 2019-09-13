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

package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.views.helpers

import play.api.data.Form
import play.api.i18n.Messages

object PageTitleHelper {

  val separator: String = " - "
  val errorPrefixKey: String = "base.title_error_prefix"
  val govukKey: String = "base.govuk"

  def formatTitle(formOpt: Option[Form[_]], serviceName: String, title: String)(implicit messages: Messages): String = {
    val prefix = formOpt.collect { case form if form.hasErrors => messages(errorPrefixKey) }.getOrElse("")
    val body = if (serviceName == title) serviceName else title + separator + serviceName
    val suffix = separator + messages(govukKey)

    prefix + body + suffix
  }
}