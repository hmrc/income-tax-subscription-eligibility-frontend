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

package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config

import play.api.Configuration
import play.api.i18n.Lang
import play.api.mvc.Call
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers.agents.{routes => agentRoutes}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers.principal.{routes => principalRoutes}
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig

import javax.inject.{Inject, Singleton}

@Singleton
class AppConfig @Inject()(config: ServicesConfig, val configuration: Configuration) {

  val appName: String = config.getString("appName")

  private def loadConfig(key: String) = config.getString(key)

  private val incometaxSubscriptionFrontendHost = loadConfig("income-tax-subscription-frontend.host")
  private val incometaxSubscriptionFrontendFirstPageUrl = "/report-quarterly/income-and-expenses/sign-up"
  private val incometaxSubscriptionFrontendAgentIncomeSourcesPageUrl = "/report-quarterly/income-and-expenses/sign-up/client/eligibility/income-sources"


  private val contactBaseUrl = loadConfig("contact-frontend.host")
  protected lazy val contactHost: String = config.getString("contact-frontend.host")
  lazy val contactFormServiceIdentifier = "MTDIT"

  private val assetsUrl = loadConfig("assets.url")
  val incometaxSubscriptionFrontendFirstPageFullUrl = s"${incometaxSubscriptionFrontendHost}${incometaxSubscriptionFrontendFirstPageUrl}"
  val incometaxSubscriptionFrontendAgentIncomeSourcesPageFullUrl = s"${incometaxSubscriptionFrontendHost}${incometaxSubscriptionFrontendAgentIncomeSourcesPageUrl}"


  val serviceIdentifier = "MTDIT"

  val assetsPrefix: String = assetsUrl + loadConfig("assets.version")
  val analyticsToken: String = loadConfig(s"google-analytics.token")
  val analyticsHost: String = loadConfig(s"google-analytics.host")

  val reportAProblemPartialUrl: String = s"$contactBaseUrl/contact/problem_reports_ajax?service=$serviceIdentifier"
  val reportAProblemNonJSUrl: String = s"$contactBaseUrl/contact/problem_reports_nonjs?service=$serviceIdentifier"

  lazy val govUkUrl: String = loadConfig("gov-uk.url")
  lazy val govukGuidanceITSASignUpIndivLink: String = s"$govUkUrl/guidance/sign-up-your-business-for-making-tax-digital-for-income-tax"
  lazy val govukGuidanceITSASignUpAgentLink: String = s"$govUkUrl/guidance/sign-up-your-client-for-making-tax-digital-for-income-tax"
  lazy val urBannerUrl: String = loadConfig("urBannerUrl.url")

  lazy val guidanceUrl: String = s"$govUkUrl/guidance/use-software-to-send-income-tax-updates"
  lazy val workingForYourselfUrl: String = s"$govUkUrl/working-for-yourself"

  lazy val  qualifyingIncomeUrl: String = s"$govUkUrl/guidance/check-if-youre-eligible-for-making-tax-digital-for-income-tax#find-out-about-qualifying-income"
  lazy val saTaxReturnLink: String = s"$govUkUrl/self-assessment-tax-returns"
  lazy val sendingReturnGovUkUrl: String = s"$govUkUrl/self-assessment-tax-returns/sending-return"

  def languageMap: Map[String, Lang] = Map(
    "english" -> Lang("en"),
    "cymraeg" -> Lang("cy")
  )

  def routeToSwitchLanguage: String => Call = (lang: String) => principalRoutes.LanguageSwitchController.switchToLanguage(lang)

  def routeToSwitchAgentLanguage: String => Call = (lang: String) => agentRoutes.LanguageSwitchController.switchToLanguage(lang)

  lazy val betaFeedbackUnauthenticatedUrl: String =
    s"$contactHost/contact/beta-feedback-unauthenticated?service=$contactFormServiceIdentifier"

}
