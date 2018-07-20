import Counter.Count
import akka.actor.{ActorRef, ActorSystem}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

object Main extends App {
  val system = ActorSystem("lug-sys")
  implicit val ec: ExecutionContext = system.dispatcher

  val list = (0 to 99999).toList

 val actorRefs: Seq[ActorRef] = list map { i =>
  system.actorOf(Counter.props, s"CounterActor-$i")
 }

  actorRefs foreach { actorRef =>
    list foreach { _ =>
      actorRef ! Count
    }
  }

//  system.scheduler.schedule(0 millis, 10 millis,counterActor, Count)
}
