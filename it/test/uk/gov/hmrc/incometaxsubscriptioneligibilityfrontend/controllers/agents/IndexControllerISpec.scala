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

import play.api.libs.ws.WSResponse
import play.api.test.Helpers._
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.featureswitch.FeatureSwitch.SignUpEligibilityInterrupt
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.featureswitch.FeatureSwitching
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.ComponentSpecBase

class IndexControllerISpec extends ComponentSpecBase with FeatureSwitching {

  def result(): WSResponse = get("/client")

  "GET /client" when {
    "interrupt feature switch is not set" should {
      s"return $SEE_OTHER" in {
        result() must have(
          httpStatus(SEE_OTHER),
          redirectUri(appConfig.incomeTaxSubscriptionFrontendAgentIncomeSourcesPageFullUrl)
        )
      }
    }
    "interrupt feature switch is set" should {
      s"return $SEE_OTHER" in {
        enable(SignUpEligibilityInterrupt)
        result() must have(
          httpStatus(SEE_OTHER),
          redirectUri(routes.SigningUpController.show.url)
        )
      }
    }
  }

}
