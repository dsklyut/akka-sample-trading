package org.samples.trading.actorbang

import org.samples.trading.actor._
import org.samples.trading.domain.Order
import org.samples.trading.domain.Rsp

class ActorBangOrderReceiver(val matchingEngines2: List[ActorMatchingEngine])
  extends ActorOrderReceiver(matchingEngines2) {

  override protected def placeOrder(order: Order) = {
    if (matchingEnginePartitionsIsStale) refreshMatchingEnginePartitions
    val matchingEngine = matchingEngineForOrderbook(order.orderbookSymbol)
    matchingEngine match {
      case Some(m) =>
      //         println("receiver " + order)
        m.forward(order)
      case None =>
        println("Unknown orderbook: " + order.orderbookSymbol)
        reply(new Rsp(false))
    }
  }

}
