import scala.async.Async.{await}
import scala.concurrent.{Promise, Await}
import scala.concurrent.duration._
import scala.util.Try

package net.alanc {

import scala.concurrent.{ExecutionContext, Future}

class ScalaSubject[T](duration: Duration) {
  println("FINDME FINDME FINDME FINDME FINDME FINDME FINDME FINDME FINDME FINDME ")
  private var p = Promise[T]()

  private def loop: Stream[T] = {
    val current = p
    Await.result(current.future, duration) #:: loop
  }

  lazy val iterable = loop.toIterable

  def onNext(v: T) {
    val current = p
    p = Promise[T]()
    println("Success");
    p.success(v)
    //current.completeWith(Future(v)(ExecutionContext.global))
  }
}

}
