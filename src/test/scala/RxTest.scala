package rx.lang.scala

import net.alanc.ScalaSubject

import scala.concurrent.duration._
import scala.language.postfixOps
import org.scalatest._
import net.alanc.sampleProtocol._
import net.alanc.rx._

//class RxTest extends FlatSpec with Matchers {
////  "ScalaSubject" should "emit data when onNext is called" in {
////    println("1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 ")
////    val sub = new ScalaSubject[Int](1 seconds)
////    sub.onNext(1)
////    sub.onNext(2)
////    sub.iterable.head should be (1)
////    sub.iterable.tail should be (List.empty)
////    sub.iterable.tail.head should be (1)
////  }
////  "toList" should "convert an observable to a list" in {
////    val data = List(1,2,3,4,5);
////    val obs = Observable.from(data)
////
////    toList(obs, 5, 1 seconds) should be (data)
////
////  }
////  "toChars" should "split a s = Subject[Int]()list of strings into a list of characters" in {
////    val strings = "Hello" :: "World" :: Nil;
////    val chars = 'H'::'e'::'l'::'l'::'o'::'W'::'o'::'r'::'l'::'d'::Nil
////    toChars(strings) should be (chars)
////    val l : List[Char] = toList(toChars(Observable.from(strings)), 10, 1 seconds)
////    l should be (chars)
////  }
//}