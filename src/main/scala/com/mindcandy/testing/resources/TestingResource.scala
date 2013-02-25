package com.mindcandy.testing.resources

import unfiltered.request._
import unfiltered.response._
import unfiltered.netty.cycle.Plan
import unfiltered.netty.cycle.ThreadPool
import org.jboss.netty.channel.ChannelHandlerContext

class TestingResource extends Plan with ThreadPool {

  def intent = {
    case req @ Path(Seg("testing" :: Nil)) => req match {
      case GET(_) => ResponseString("Hello, testing")
      case _      => MethodNotAllowed ~> ResponseString("Only GET supported\n")
    }
    case _ => ResponseString("Request not valid\n")
  }

  def onException(ctx: ChannelHandlerContext, t: Throwable) {
    //Be sure to add proper error handling
    println("Something went wrong")
  }

}