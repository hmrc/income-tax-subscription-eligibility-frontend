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


import play.api.data.Form
import play.api.i18n.I18nSupport
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents, Request}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.AppConfig
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.forms.AreYouSoleTraderAfterForm._
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.implicits.ImplicitDateFormatter
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.audits.EligibilityAnswerAuditing
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.audits.EligibilityAnswerAuditing.EligibilityAnswerAuditModel
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.{No, Yes, YesNo}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.services.AuditingService
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.views.html.principal.AreYouSoleTraderAfter
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import uk.gov.hmrc.play.language.LanguageUtils

import java.time.LocalDate
import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton
class SoleTraderStartAfterController @Inject()(auditService: AuditingService,
                                               areYouSoleTraderAfter: AreYouSoleTraderAfter,
                                               mcc: MessagesControllerComponents,
                                               val languageUtils: LanguageUtils)
                                              (implicit appConfig: AppConfig) extends FrontendController(mcc) with I18nSupport with ImplicitDateFormatter {

  private def startDateLimit: LocalDate = LocalDate.now.minusYears(2)

  private def form(startDate: LocalDate)(implicit request: Request[_]): Form[YesNo] = areYouSoleTraderAfterForm(startDate.toLongDate)

  def show: Action[AnyContent] = Action.async {
    implicit request =>
      Future.successful(
        Ok(areYouSoleTraderAfter(form(startDateLimit), routes.SoleTraderStartAfterController.submit, startDateLimit.toLongDate, backUrl = backUrl))
      )
  }

  def submit: Action[AnyContent] = Action.async {
    implicit request =>
      form(startDateLimit).bindFromRequest.fold(
        formWithErrors => Future.successful(BadRequest(areYouSoleTraderAfter(formWithErrors, routes.SoleTraderStartAfterController.submit, startDateLimit.toLongDate, backUrl = backUrl))), {
          case Yes =>
            auditService.audit(EligibilityAnswerAuditModel(
              userType = EligibilityAnswerAuditing.eligibilityAnswerIndividual,
              eligible = false,
              answer = "yes",
              question = "soleTraderBusinessStartDate"
            ))
            Future.successful(Redirect(routes.CannotSignUpController.show))
          case No =>
            auditService.audit(EligibilityAnswerAuditModel(
              userType = EligibilityAnswerAuditing.eligibilityAnswerIndividual,
              eligible = true,
              answer = "no",
              question = "soleTraderBusinessStartDate"
            ))
            Future.successful(Redirect(routes.PropertyTradingStartAfterController.show))
        }
      )
  }

  def backUrl: String = {
    routes.HaveAnyOtherIncomeController.show.url
  }

}
