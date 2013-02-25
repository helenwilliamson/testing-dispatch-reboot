package com.mindcandy.testing.resources

import com.mindcandy.testing.resources.util.Served
import org.specs2.mutable.Specification
import com.ning.http.client
import unfiltered.response.MethodNotAllowed

object CodeAndBody extends (client.Response => (Int, String)) {
  def apply(r: client.Response) = (r.getStatusCode, r.getResponseBody)
}

class TestingResourceTest extends Specification with Served{

  import dispatch._

  def setup = _.plan(new TestingResource)

  "The project TestingResources" should {
    "serve GET" in {
      val svc = host / "testing"

      try{
        val response = Http(svc OK as.String)
        response() must beMatching("Hello, testing")
      } finally{
        Http.shutdown()
      }
    }
    "not serve POST" in {
      val svc = host / "testing" << "something"

      try{
        val actualResponse = Http(svc > CodeAndBody)
        val expectedResponse = (MethodNotAllowed.code, "Only GET supported\n")
        actualResponse() must beEqualTo(expectedResponse)
      } finally{
        Http.shutdown()
      }
    }
  }

}