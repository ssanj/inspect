package net.ssanj.runner

import monix.execution.Scheduler.Implicits.global
import monix.reactive._
import concurrent.duration._

object MacroRunner {
  def main(args: Array[String]): Unit = {
    zen.t(List(1,2,3,4))
    zen.t(List(1,2,3,4).map(_.toString).mkString)

    val source = zen.inspect(Observable.interval(1.second)
      .filter(_ % 2 == 0)
      .flatMap(x => Observable(x, x)))
      . take(11)

    val cancelable = zen.ast(source
      .dump("O"))
      .subscribe()

    val x =
      zen.explain(for {
        one <- Option(1)
        two <- Option(2)
        if (one > 5)
      } yield (one + two))


    zen.explain { Option(1) match {
        case Some(x) => true
        case None => false
      }
    }

    Observable.range(0, 4).dump("O")
      .consumeWith(Consumer.complete)
      .runAsync
      .foreach(x => { zen.t("x", x); println("Consumer completed") })
  }
}