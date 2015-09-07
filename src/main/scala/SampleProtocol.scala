import rx.lang.scala._
import net.alanc.rx._

package net.alanc.sampleProtocol {

import net.alanc.StringProtocol

import scala.io.Source

trait Message

  case class Hello(name: String) extends Message

  case class Ident(name: String) extends Message

  case class Chat(from: String, to: String, msg: String) extends Message


  class SampleProtocol() extends StringProtocol[Message] {
    def decode(line:String):Message = {
      line.split('~').toList match {
        case "HELLO" :: msg :: Nil => new Hello(msg)
        case "IDENT" :: name :: Nil => new Ident(name)
        case "CHAT" :: from :: to :: msg :: Nil => new Chat(from, to, msg)
        case _ => new Hello("sorry")
      }
    }
    def encode(msg:Message):String = {
      (msg match {
        case Hello(name) => "HELLO" :: name :: Nil;
        case Ident(name) => "IDENT" :: name :: Nil;
        case Chat(from, to, msg) => "CHAT" :: from :: to :: msg :: Nil;
      }).mkString("~")
    }
  }

}
