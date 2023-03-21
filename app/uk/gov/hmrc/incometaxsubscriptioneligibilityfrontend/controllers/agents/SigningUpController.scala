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

package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers.agents

import play.api.i18n.I18nSupport
import play.api.mvc._
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.AppConfig
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.featureswitch.FeatureSwitching
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.subscription.AccountingPeriodUtil
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.views.html.agents.SigningUp

import javax.inject.{Inject, Singleton}

@Singleton
class SigningUpController @Inject()(mcc: MessagesControllerComponents, signingUp: SigningUp)
                                   (implicit val appConfig: AppConfig) extends FrontendController(mcc) with I18nSupport with FeatureSwitching {

  val show: Action[AnyContent] = Action { implicit request =>
    Ok(signingUp(postAction = routes.SigningUpController.submit, AccountingPeriodUtil.getCurrentTaxYear, AccountingPeriodUtil.getNextTaxYear, backLink))
  }

  val submit: Action[AnyContent] = Action { _ =>
    Redirect(appConfig.incometaxSubscriptionFrontendAgentIncomeSourcesPageFullUrl)
  }

  val backLink: String = appConfig.govukGuidanceITSASignUpAgentLink

}
