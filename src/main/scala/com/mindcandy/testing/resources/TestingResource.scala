package com.mindcandy.testing.resources

import unfiltered.request._
import unfiltered.response._
import unfiltered.netty.async.Plan
import unfiltered.netty.cycle.ThreadPool
import org.jboss.netty.channel.ChannelHandlerContext

class TestingResource extends Plan with ThreadPool {

  def intent = {
    case request@Path(Seg("testing" :: Nil)) => request match {
      case GET(_) => request.respond(ResponseString("Hello, testing"))
      case _      => request.respond(MethodNotAllowed ~> ResponseString("Only GET supported\n"))
    }
    case request => request.respond(ResponseString("Request not valid\n"))
  }

  def onException(ctx: ChannelHandlerContext, t: Throwable) {
    //Be sure to add proper error handling
    println("Something went wrong")
  }

}