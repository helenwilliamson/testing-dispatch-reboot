package com.mindcandy.testing.resources.util

import unfiltered.util.Port
import dispatch.:/
import unfiltered.netty.Http
import org.specs2.mutable.Specification
import org.specs2.specification.{Step, Fragments}
import com.ning.http.client

trait Served extends Specification {

  val port = Port.any
  def host() = :/("localhost", port)

  def setup: (Http => Http)

  lazy val server = setup(Http(port))

  def start() = server.start()

  def stop() = { server.stop; server.destroy }

  override def map(fs: => Fragments) = Step(start()) ^ fs ^ Step(stop())

}

object CodeAndBody extends (client.Response => (Int, String)) {
  def apply(r: client.Response) = (r.getStatusCode, r.getResponseBody)
}