
package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.controllers.principal

import play.api.libs.ws.WSResponse
import play.api.test.Helpers._
import uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.ComponentSpecBase

class IndexControllerISpec extends ComponentSpecBase {

  lazy val result: WSResponse = get("/")

  s"GET ${routes.IndexController.index().url}" should {
    s"return $SEE_OTHER" in {
      result must have(
        httpStatus(SEE_OTHER),
        redirectUri(routes.OverviewController.show().url)
      )
    }
  }

}
