package com.mindcandy.testing.resources

import util.{CodeAndBody, Served}
import org.specs2.mutable.Specification
import unfiltered.response.MethodNotAllowed

class TestingResourceAgainTest extends Specification with Served{

  import dispatch._

  def setup = _.plan(new TestingResource)

  "The project TestingResources again" should {
    "not serve POST" in {
      val svc = host() / "testing" << "something"

      try{
        val actualResponse = Http(svc > CodeAndBody)
        val expectedResponse = (MethodNotAllowed.code, "Only GET supported\n")
        actualResponse() must beEqualTo(expectedResponse)
      } finally{
        Http.shutdown()
      }
    }
    "serve GET" in {
      val svc = host() / "testing"

      try{
        val response = Http(svc OK as.String)
        response() must beMatching("Hello, testing")
      } finally{
        Http.shutdown()
      }
    }
  }

}