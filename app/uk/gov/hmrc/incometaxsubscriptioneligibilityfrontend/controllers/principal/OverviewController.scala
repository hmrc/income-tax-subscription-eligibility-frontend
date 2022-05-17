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
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.featureswitch.{FeatureSwitching, RemoveCovidPages}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.views.html.principal.injected.Overview
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future


@Singleton
class OverviewController @Inject()(overview: Overview, mcc: MessagesControllerComponents)
                                  (implicit val appConfig: AppConfig) extends FrontendController(mcc) with I18nSupport with FeatureSwitching {

  val show: Action[AnyContent] = Action.async {
    implicit request =>
      if (isEnabled(RemoveCovidPages)) {
        Future.successful(
          Ok(overview(postAction = routes.HaveAnyOtherIncomeController.show))
        )
      } else {
        Future.successful(
          Ok(overview(postAction = routes.Covid19ClaimCheckController.show))
        )
      }
  }
}
