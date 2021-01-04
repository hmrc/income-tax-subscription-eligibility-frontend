/*
 * Copyright 2021 HM Revenue & Customs
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

import java.time.LocalDate

import javax.inject.{Inject, Singleton}
import play.api.i18n.I18nSupport
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.AppConfig
import uk.gov.hmrc.play.bootstrap.controller.FrontendController
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.forms.PropertyTradingStartDateForm._
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.{No, Yes}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.views.html.principal.property_trading_after
import uk.gov.hmrc.play.language.LanguageUtils
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.implicits.ImplicitDateFormatter
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.audits.EligibilityAnswerAuditing
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.audits.EligibilityAnswerAuditing.EligibilityAnswerAuditModel
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.services.AuditingService

import scala.concurrent.Future

@Singleton
class PropertyTradingStartAfterController @Inject()(auditService: AuditingService, mcc: MessagesControllerComponents,
                                                    val languageUtils: LanguageUtils)
                                                   (implicit appConfig: AppConfig) extends FrontendController(mcc) with I18nSupport with ImplicitDateFormatter {

  private def startDateLimit: LocalDate = LocalDate.now.minusYears(1)

  def show: Action[AnyContent] = Action.async {
    implicit request =>
      Future.successful(Ok(property_trading_after(propertyTradingStartDateForm(startDateLimit.toLongDate),
        routes.PropertyTradingStartAfterController.submit(), startDateLimit.toLongDate)))
  }

  def submit(): Action[AnyContent] = Action.async {
    implicit request =>
      propertyTradingStartDateForm(startDateLimit.toLongDate).bindFromRequest.fold(
        formWithErrors => Future.successful(BadRequest(property_trading_after(
          formWithErrors, routes.PropertyTradingStartAfterController.submit(), startDateLimit.toLongDate))), {
          case Yes =>
            auditService.audit(EligibilityAnswerAuditModel(EligibilityAnswerAuditing.eligibilityAnswerIndividual, false, "yes",
            "propertyBusinessStartDate"))
            Future.successful(Redirect(routes.CannotSignUpController.show()))
          case No =>
            auditService.audit(EligibilityAnswerAuditModel(EligibilityAnswerAuditing.eligibilityAnswerIndividual, true, "no",
              "propertyBusinessStartDate"))
            Future.successful(Redirect(routes.CheckAccountingPeriodController.show()))
        }
      )
  }
}
