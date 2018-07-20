import Counter.Count
import akka.actor.{Actor, Props}

case class Counter() extends Actor {
  var x = 0
  override def receive: Receive = {
    case Count =>
      x += 1
      println(x)
//      println(self.path)

  }
}

object Counter {
  def props = Props(Counter())

  object Count
}
