
package uk.gov.hmrc.incometaxsubscriptioneligibilityfrontend.utils.servicemocks

import play.api.libs.json.JsValue


object AuditStub extends WireMockMethods {

  def stubAuditing(): Unit = {
    when(method = POST, uri = "/write/audit/merged")
      .thenReturn(status = 200, body = """{"x":2}""")

    when(method = POST, uri = "/write/audit")
      .thenReturn(status = 200, body = """{"x":2}""")
  }

  def verifyAudit(): Unit = {
    //We cannot verify content of audit body without string matching/regex
    //It is tested in more detail at unit level
    verify(method = POST, uri = "/write/audit")
  }

  def verifyAuditContains(body: JsValue): Unit = {
    verifyContains(method = POST, uri = "/write/audit", body)
  }
}


