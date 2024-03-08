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

package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers.principal

import play.api.i18n.I18nSupport
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents, Request}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.AppConfig
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.featureswitch.FeatureSwitch.SignUpEligibilityInterrupt
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.featureswitch.FeatureSwitching
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.forms.AccountingPeriodForm.accountingPeriodForm
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.AccountingPeriod
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.AccountingPeriod._
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.audits.EligibilityAnswerAuditing.EligibilityAnswerAuditModel
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.services.AuditingService
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.views.html.principal.AccountingPeriodCheck
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CheckAccountingPeriodController @Inject()(accountingPeriodCheck: AccountingPeriodCheck,
                                                auditService: AuditingService,
                                                mcc: MessagesControllerComponents)
                                               (implicit val appConfig: AppConfig,
                                                executionContext: ExecutionContext) extends FrontendController(mcc) with I18nSupport with FeatureSwitching {

  def show: Action[AnyContent] = Action.async {
    implicit request =>
      Future.successful(
        Ok(accountingPeriodCheck(accountingPeriodForm, routes.CheckAccountingPeriodController.submit, backUrl = backUrl))
      )
  }

  def submit: Action[AnyContent] = Action.async {
    implicit request =>
      accountingPeriodForm.bindFromRequest().fold(
        formWithErrors => Future.successful(BadRequest(accountingPeriodCheck(
          formWithErrors,
          routes.CheckAccountingPeriodController.submit,
          backUrl = backUrl
        ))), {
          case SixthAprilToFifthApril =>
            audit(eligible = true, answer = SixthAprilToFifthApril)
              .map(_ => Redirect(routes.SignUpController.show))
          case FirstAprilToThirtyFirstMarch =>
            audit(eligible = true, answer = FirstAprilToThirtyFirstMarch)
              .map(_ => Redirect(routes.SignUpController.show))
          case OtherAccountingPeriod =>
            audit(eligible = false, answer = OtherAccountingPeriod)
              .map(_ => Redirect(routes.CannotSignUpController.show))
        }
      )
  }

  private def backUrl: String = {
    if (isEnabled(SignUpEligibilityInterrupt)) {
      routes.SigningUpController.show.url
    } else {
      routes.OverviewController.show.url
    }
  }

  private def audit(eligible: Boolean, answer: AccountingPeriod)
                   (implicit request: Request[AnyContent]): Future[Unit] = {
    auditService.audit(EligibilityAnswerAuditModel(
      eligible = eligible,
      answer = answer match {
        case SixthAprilToFifthApril => "6 april to 5 april"
        case FirstAprilToThirtyFirstMarch => "1 april to 31 march"
        case OtherAccountingPeriod => "other"
      },
      question = "standardAccountingPeriod"
    ))
  }

}
