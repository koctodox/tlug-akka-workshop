import Counter.{Count, StopMe}
import SuperVisor.{Stop, Test}
import akka.actor.{Actor, ActorRef, Props}

case class Counter() extends Actor {
  val indx = Main.threeshold
  var x = 0
  override def receive: Receive = {
    case Stop(superVisorRef) =>
      if (x == indx) {
        superVisorRef ! StopMe(self) // این تیکه اضافیه و میتونیم همینجا خود اکتور رو بکشیم
      } else {
        self ! Stop(superVisorRef)
      }

    case Count =>
      x += 1
      Thread.sleep(1)

      if(x == indx){
        println("END")
      }
//      println(x)
//      println(self.path)

  }

  override def postStop(): Unit = {
    println(s">>>>>>>>>> STOP [Counter]")
    super.postStop()
  }

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    println("########### Restarted [Counter]")
    super.preRestart(reason, message)
  }

}

object Counter {
  def props = Props(Counter())

  object Count

  final case class StopMe(ref: ActorRef)
}
