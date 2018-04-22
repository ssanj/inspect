package net.ssanj.inspect

import ShowTMacros._

import monix.execution.Scheduler.Implicits.global
import monix.reactive._
import concurrent.duration._

object MacroRunner {
  def main(args: Array[String]): Unit = {
    // showT(List(1,2,3,4))
    // showT(List(1,2,3,4).map(_.toString).mkString)
    val source = inspect(Observable.interval(1.second)
      // Filtering out odd numbers, making it emit every 2 seconds
      .filter(_ % 2 == 0)
      // We then make it emit the same element twice
      .flatMap(x => Observable(x, x)))
      // This stream would be infinite, so we limit it to 10 items
      .take(10)

    val cancelable = showA(source
      // On consuming it, we want to dump the contents to stdout
      // for debugging purposes
      .dump("O"))
      // Finally, start consuming it
      .subscribe()

    val l: Either[String, Int] = Left("error")
    showT(l)

    val x =
      explain(for {
        one <- Option(1)
        two <- Option(2)
        if (one > 5)
      } yield (one + two))

  }
}