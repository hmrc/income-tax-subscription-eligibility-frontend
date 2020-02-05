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

package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config

import javax.inject.{Inject, Singleton}
import play.api.Configuration
import play.api.i18n.Lang
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers.routes
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig

@Singleton
class AppConfig @Inject()(config: ServicesConfig) {

  private def loadConfig(key: String) = config.getString(key)

  private val incometaxSubscriptionFrontendHost =  loadConfig("income-tax-subscription-frontend.host")
  private val incometaxSubscriptionFrontendFirstPageUrl = "/report-quarterly/income-and-expenses/sign-up/user-details"
  private val incometaxSubscriptionFrontendAgentFirstPageUrl = "/report-quarterly/income-and-expenses/sign-up/client/client-details"

  private val contactBaseUrl = loadConfig("contact-frontend.host")

  private val assetsUrl = loadConfig("assets.url")
  val incometaxSubscriptionFrontendFirstPageFullUrl = s"${incometaxSubscriptionFrontendHost}${incometaxSubscriptionFrontendFirstPageUrl}"
  val incometaxSubscriptionFrontendAgentFirstPageFullUrl = s"${incometaxSubscriptionFrontendHost}${incometaxSubscriptionFrontendAgentFirstPageUrl}"
  private val serviceIdentifier = "MTDIT"

  val assetsPrefix: String = assetsUrl + loadConfig("assets.version")
  val analyticsToken: String = loadConfig(s"google-analytics.token")
  val analyticsHost: String = loadConfig(s"google-analytics.host")

  val reportAProblemPartialUrl: String = s"$contactBaseUrl/contact/problem_reports_ajax?service=$serviceIdentifier"
  val reportAProblemNonJSUrl: String = s"$contactBaseUrl/contact/problem_reports_nonjs?service=$serviceIdentifier"

  lazy val govUkUrl: String = loadConfig("gov-uk.url")

  lazy val guidanceUrl: String = s"$govUkUrl/guidance/use-software-to-send-income-tax-updates"

  lazy val softwareOptionsUrl: String = s"$govUkUrl/guidance/find-software-thats-compatible-with-making-tax-digital-for-income-tax"

  val checkCompatibleSoftware: String = s"$govUkUrl/guidance/find-software-thats-compatible-with-making-tax-digital-for-income-tax"

  def languageMap: Map[String, Lang] = Map(
    "english" -> Lang("en"),
    "cymraeg" -> Lang("cy"))

  def routeToSwitchLanguage = (lang: String) => routes.LanguageSwitchController.switchToLanguage(lang)

}
