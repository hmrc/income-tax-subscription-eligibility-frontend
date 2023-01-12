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

package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils

import org.mockito.Mockito.reset
import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import play.api.Configuration
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.AppConfig
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.config.featureswitch.{FeatureSwitch, FeatureSwitching}
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig

class FeatureSwitchingSpec extends PlaySpec with MockitoSugar with FeatureSwitching with BeforeAndAfterEach {
  val mockServicesConfig: ServicesConfig = mock[ServicesConfig]
  val mockConfig: Configuration = mock[Configuration]
  val appConfig = new AppConfig(mockServicesConfig, mockConfig)

  override def beforeEach(): Unit = {
    super.beforeEach()
    reset(mockConfig)
    FeatureSwitch.switches foreach { switch =>
      sys.props -= switch.name
    }
  }

  "FeatureSwitching constants" should {
    "be true" in {
      FEATURE_SWITCH_ON mustBe "true"
    }

    "be false" in {
      FEATURE_SWITCH_OFF mustBe "false"
    }
  }

  "List of feature switches" should {
    "be one" in {
      // This test will fail when you add a switch, at which point you should uncomment the tests below
      FeatureSwitch.switches.size mustBe (1)
    }
  }


// Current feature switch list is: []
// As a result we can't simply write tests as the trait is sealed.  So, commenting as we will want this code later.
//  "RemoveCovidPages" should {
//    "return true if RemoveCovidPages feature switch is enabled in sys.props" in {
//      enable(RemoveCovidPages)
//      isEnabled(RemoveCovidPages) mustBe true
//    }
//    "return false if RemoveCovidPages feature switch is disabled in sys.props" in {
//      disable(RemoveCovidPages)
//      isEnabled(RemoveCovidPages) mustBe false
//    }
//
//    "return false if RemoveCovidPages feature switch does not exist" in {
//      when(mockConfig.getOptional[String]("feature-switch.remove-covid-eligibility-and-kickout-page")).thenReturn(None)
//      isEnabled(RemoveCovidPages) mustBe false
//    }
//
//    "return false if RemoveCovidPages feature switch is not in sys.props but is set to 'off' in config" in {
//      when(mockConfig.getOptional[String]("feature-switch.remove-covid-eligibility-and-kickout-page")).thenReturn(Some(FEATURE_SWITCH_OFF))
//      isEnabled(RemoveCovidPages) mustBe false
//    }
//
//    "return true if RemoveCovidPages feature switch is not in sys.props but is set to 'on' in config" in {
//      when(mockConfig.getOptional[String]("feature-switch.remove-covid-eligibility-and-kickout-page")).thenReturn(Some(FEATURE_SWITCH_ON))
//      isEnabled(RemoveCovidPages) mustBe true
//    }
//  }
}