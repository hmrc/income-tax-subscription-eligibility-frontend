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

package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.testonly.controllers

import play.api.i18n.I18nSupport
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents, Request}
import play.twirl.api.Html
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.AppConfig
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.featureswitch.FeatureSwitch.switches
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.featureswitch.{FeatureSwitch, FeatureSwitching}
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.testonly.views.html.FeatureSwitchView
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import javax.inject.Inject
import scala.collection.immutable.ListMap
import scala.concurrent.ExecutionContext

class FeatureSwitchController @Inject()(featureSwitch: FeatureSwitchView)
                                       (implicit val ec: ExecutionContext,
                                        implicit val appConfig: AppConfig,
                                        mcc: MessagesControllerComponents) extends FrontendController(mcc) with FeatureSwitching with I18nSupport {

  private def view(switchNames: Map[FeatureSwitch, Boolean])(implicit request: Request[_]): Html =
    featureSwitch(
      switchNames = switchNames,
      uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.testonly.controllers.routes.FeatureSwitchController.submit
    )


  lazy val show: Action[AnyContent] = Action { implicit req =>
    val featureSwitches = ListMap(switches.toSeq sortBy (_.displayText) map (switch => switch -> isEnabled(switch)): _*)
    Ok(view(featureSwitches))
  }

  lazy val submit: Action[AnyContent] = Action { implicit req =>

    val submittedData: Set[String] = req.body.asFormUrlEncoded match {
      case None => Set.empty
      case Some(data) => data.keySet
    }

    val featureSwitches = submittedData flatMap FeatureSwitch.get

    switches.foreach(fs =>
      if (featureSwitches.contains(fs)) enable(fs)
      else disable(fs)
    )

    Redirect(uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.testonly.controllers.routes.FeatureSwitchController.show)
  }
}