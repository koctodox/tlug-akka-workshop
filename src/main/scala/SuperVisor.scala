import Counter.{Count, StopMe}
import SuperVisor.{Start, Stop, Test}
import akka.actor.{Actor, ActorRef, PoisonPill, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.duration._
import scala.concurrent.duration.Duration
import scala.concurrent.Await

case class SuperVisor() extends Actor {
  //  implicit val timeout: Timeout = Timeout(500 seconds)
  val indx = Main.threeshold
  var killedChiled = 0

  override def receive: Receive = {
    case StopMe(ref) =>
      context.stop(ref)
      killedChiled += 1
      if (killedChiled == indx) {
        context.stop(self)
      }

    case Stop(ref) =>
      context.children foreach { child =>
        child ! Stop(self)
      }

    case Start =>
      val list = (1 to indx).toList
      val actorRefs: Seq[ActorRef] = list map { i =>
        context.actorOf(Counter.props, s"$i")
      }

      actorRefs map { actorRef =>
        list map { _ =>
          actorRef ! Count
        }
      }

      self ! Stop(self)

  }

  override def preStart(): Unit = {
    println(s"%%%% Started [SuperVisor]")
    super.preStart()
  }

  override def postStop(): Unit = {
    println(s"########### STOP [SuperVisor]")
    super.postStop()
  }

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    println("########### Restarted [SuperVisor]")
    super.preRestart(reason, message)
  }

}

object SuperVisor {
  def props = Props(SuperVisor())

  final case class Stop(ref: ActorRef)

  object Start

  object Test

}
