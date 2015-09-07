import rx.lang.scala.Observable
import rx.lang.scala.Subject

package net.alanc {


import scala.collection.mutable
import scala.concurrent.duration.Duration

object rx {

    def split[T](input: Observable[T], doSplit: (T) => Boolean): Observable[Iterable[T]] = {
      input
        .scan((false, List.empty[T]))((acc: (Boolean, List[T]), x: T) => {
        val clear = acc._1
        val list = if (clear) List.empty[T] else acc._2
        if (doSplit(x)) (true, list)
        else (false, x :: list)
      })
        .filter(x => x._1)
        .map(x => x._2.reverse)
    }

    def split[T <: AnyVal](input: Observable[T], delimiter: T): Observable[Iterable[T]] = split(input, (x: T) => x == delimiter)

    def split[T](input: Iterable[T], doSplit: (T) => Boolean): Iterable[Iterable[T]] = {
      input
        .scanLeft((false, List.empty[T]))((acc: (Boolean, List[T]), x: T) => {
        val clear = acc._1
        val list = if (clear) List.empty[T] else acc._2
        if (doSplit(x)) (true, list)
        else (false, x :: list)
      })
        .filter(x => x._1)
        .map(x => x._2.reverse)
    }

    def split[T <: AnyVal](input: Iterable[T], delimiter: T): Iterable[Iterable[T]] = split(input, (x: T) => x == delimiter)

    def byLine(input:Iterable[Char]) = split(input, '\n').map(line => line.mkString(""))
    def byLine(input:Observable[Char]) = split(input, '\n').map(line => line.mkString(""))

    def readLines(input:Iterable[Char], length:Int) = byLine(input).take(length).toList
    def readLines(input:Observable[Char], length:Int) = byLine(input).take(length).toList

    def toChars(input:Iterable[String]) = input.flatten
    def toChars(input:Observable[String]):Observable[Char] = input.flatMap((str:String) => Observable.from(str))

    def pipe[T](input:Iterable[T], output:Subject[T]) = input.foreach(x => output.onNext(x))
    def pipe[T](input:Observable[T], output:mutable.Queue[T]) = input.subscribe(t => output.enqueue(t))
    def pipe[T](input:Observable[T], output:Subject[T]) = input.subscribe(t => output.onNext(t))

    def toQueue[T](input:Observable[T]): mutable.Queue[T] = {
      val q = mutable.Queue[T]()
      pipe(input, q)
      q
    }

    def toIterable[T](input:Observable[T], duration: Duration) = {
      val subject = new ScalaSubject[T](duration)
      input.subscribe((item:T) => subject.onNext(item))
      subject.iterable
    }
    def toList[T](input:Observable[T], length:Int, duration: Duration):List[T] = toIterable(input, duration).take(length).toList
    def toList[T](input:Observable[T], length:Int):List[T] = toList(input, length, Duration.Inf)

  }

}