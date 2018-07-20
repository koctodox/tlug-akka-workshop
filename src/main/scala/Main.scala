import SuperVisor.{Start, Stop}
import akka.actor.{ActorSystem, PoisonPill}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

object Main extends App {
  val system = ActorSystem("lug-sys")
  implicit val ec: ExecutionContext = system.dispatcher
  val superVisor = system.actorOf(SuperVisor.props, "SuperVisorActor")
  val threeshold: Int = 50

  superVisor ! Start

//  system.scheduler.schedule(0 millis, 50 seconds, superVisor, Start)
}
