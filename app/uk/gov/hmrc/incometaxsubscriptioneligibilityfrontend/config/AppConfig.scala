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
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig

import javax.inject.{Inject, Singleton}

@Singleton
class AppConfig @Inject()(config: ServicesConfig, val configuration: Configuration) {

  val appName: String = config.getString("appName")

  private def loadConfig(key: String) = config.getString(key)

  private val incomeTaxSubscriptionFrontendHost = loadConfig("income-tax-subscription-frontend.host")
  private val incomeTaxSubscriptionFrontendFirstPageUrl = "/report-quarterly/income-and-expenses/sign-up"
  private val incomeTaxSubscriptionFrontendAgentIncomeSourcesPageUrl = "/report-quarterly/income-and-expenses/sign-up/client"

  private lazy val contactHost: String = config.getString("contact-frontend.host")
  private lazy val contactFormServiceIdentifier = "MTDIT"

  val incomeTaxSubscriptionFrontendFirstPageFullUrl: String =
    s"$incomeTaxSubscriptionFrontendHost$incomeTaxSubscriptionFrontendFirstPageUrl"

  val incomeTaxSubscriptionFrontendAgentIncomeSourcesPageFullUrl: String =
    s"$incomeTaxSubscriptionFrontendHost$incomeTaxSubscriptionFrontendAgentIncomeSourcesPageUrl"

  lazy val govUkUrl: String = loadConfig("gov-uk.url")
  lazy val govukGuidanceITSASignUpIndivLink: String = s"$govUkUrl/guidance/sign-up-your-business-for-making-tax-digital-for-income-tax"
  lazy val govukGuidanceITSASignUpAgentLink: String = s"$govUkUrl/guidance/sign-up-your-client-for-making-tax-digital-for-income-tax"
  lazy val urBannerUrl: String = loadConfig("urBannerUrl.url")

  lazy val guidanceUrl: String = s"$govUkUrl/guidance/use-software-to-send-income-tax-updates"
  lazy val workingForYourselfUrl: String = s"$govUkUrl/working-for-yourself"

  lazy val betaFeedbackUnauthenticatedUrl: String =
    s"$contactHost/contact/beta-feedback-unauthenticated?service=$contactFormServiceIdentifier"

}
