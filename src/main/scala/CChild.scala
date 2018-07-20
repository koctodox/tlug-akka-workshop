import SuperVisor.Test
import akka.actor.Actor

case class CChild() extends Actor {
  override def receive: Receive = {
    case Test =>
      println(s"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")
  }
}
