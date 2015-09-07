import rx.lang.scala._
import net.alanc.rx._

package net.alanc {

import scala.io.Codec


abstract class StringProtocol[Message] {
  val codec = Codec("utf8")
  val input = Subject[Byte]()
  val output = Subject[Byte]()
  val send = Subject[Message]()
  val receive = Subject[Message]()

  def decode(line:String):Message
  def encode(msg:Message):String

  // Pipe incoming text to received messages
  pipe(
    split(Encoding.ASCII.decode(input), '\n')
      .map(_.mkString(""))
      .map(decode),
    receive
  )

  // Pipe sent messages to outgoing text
  send.map(encode)
    .foreach(line => {
    pipe(Encoding.ASCII.encode(Observable.from(line.toList)), output)
    output.onNext('\n')
  })

}

}