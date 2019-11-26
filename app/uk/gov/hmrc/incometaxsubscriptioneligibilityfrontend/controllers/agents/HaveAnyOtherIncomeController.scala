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

package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers.agents

  import javax.inject.{Inject, Singleton}
  import play.api.i18n.I18nSupport
  import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
  import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.AppConfig
  import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.forms.HaveAnyOtherIncomeForm._
  import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.models.{No, Yes}
  import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.views.html.agents._
  import uk.gov.hmrc.play.bootstrap.controller.FrontendController

  import scala.concurrent.Future

  @Singleton
  class HaveAnyOtherIncomeController @Inject()(mcc: MessagesControllerComponents)
                                              (implicit appConfig: AppConfig) extends FrontendController(mcc) with I18nSupport {

    def show: Action[AnyContent] = Action.async {
      implicit request =>
        Future.successful(
          Ok(have_any_other_income(haveAnyOtherIncomeForm, routes.HaveAnyOtherIncomeController.submit()))
        )
    }

    def submit(): Action[AnyContent] = Action.async {
      implicit request =>
        haveAnyOtherIncomeForm.bindFromRequest.fold(
          formWithErrors => Future.successful(BadRequest(have_any_other_income(formWithErrors, routes.HaveAnyOtherIncomeController.submit()))), {
            case Yes => Future.successful(Redirect(routes.OtherIncomeSourcesErrorController.show()))
            case No => Future.successful(Redirect(appConfig.incometaxSubscriptionFrontendAgentFirstPageFullUrl))
          }
        )
    }
  }
