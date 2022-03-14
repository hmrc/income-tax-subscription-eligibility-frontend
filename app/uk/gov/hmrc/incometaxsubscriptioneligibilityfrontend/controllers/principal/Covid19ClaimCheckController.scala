/*
 * Copyright 2022 HM Revenue & Customs
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
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.AppConfig
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.featureswitch.FeatureSwitching
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.forms.Covid19ClaimCheckForm.covid19ClaimCheckForm
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.audits.EligibilityAnswerAuditing
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.audits.EligibilityAnswerAuditing.EligibilityAnswerAuditModel
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.{No, Yes}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.services.AuditingService
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.views.html.principal.injected.Covid19ClaimCheck
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton
class Covid19ClaimCheckController @Inject()(covid19ClaimCheck: Covid19ClaimCheck,
                                            auditService: AuditingService, mcc: MessagesControllerComponents)
                                           (implicit appConfig: AppConfig) extends FrontendController(mcc) with I18nSupport with FeatureSwitching {

  def show: Action[AnyContent] = Action.async {
    implicit request =>
      Future.successful(
        Ok(covid19ClaimCheck(covid19ClaimCheckForm, routes.Covid19ClaimCheckController.submit, backUrl = backUrl))
      )
  }


  def submit: Action[AnyContent] = Action.async {
    implicit request =>
      covid19ClaimCheckForm.bindFromRequest.fold(
        formWithErrors => Future.successful(BadRequest(covid19ClaimCheck(formWithErrors, routes.Covid19ClaimCheckController.submit, backUrl))), {
          case Yes =>
            auditService.audit(EligibilityAnswerAuditModel(
              userType = EligibilityAnswerAuditing.eligibilityAnswerIndividual,
              eligible = false,
              answer = "yes",
              question = "claimedCovidGrant"
            ))
            Future.successful(Redirect(routes.CovidCannotSignupController.show))
          case No =>
            auditService.audit(EligibilityAnswerAuditModel(
              userType = EligibilityAnswerAuditing.eligibilityAnswerIndividual,
              eligible = true,
              answer = "no",
              question = "claimedCovidGrant"
            ))
            Future.successful(Redirect(routes.HaveAnyOtherIncomeController.show))
        }
      )
  }

  def backUrl: String = {
    routes.OverviewController.show.url
  }
}
