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
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.AppConfig
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.views.html.principal.SignUp
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import javax.inject.Inject
import scala.concurrent.Future

class SignUpController @Inject()(
                                  val signUp: SignUp
                                )(implicit appConfig: AppConfig, mcc: MessagesControllerComponents) extends FrontendController(mcc) with I18nSupport {
  def show: Action[AnyContent] = Action.async {
    implicit request =>
      Future.successful(
        Ok(signUp(
          backUrl = routes.CheckAccountingPeriodController.show.url,
          continueUrl = appConfig.incomeTaxSubscriptionFrontendFirstPageFullUrl
        ))
      )
  }
}
