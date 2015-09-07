package rx.lang.scala

import net.alanc.Encoding

import scala.language.postfixOps
import org.scalatest._
import net.alanc.sampleProtocol._
import net.alanc.rx._

class SampleProtocolTests extends FlatSpec with Matchers {
  "SampleProtocol" should "send commands" in {
    val prot = new SampleProtocol()
    var lines = toQueue(byLine(Encoding.ASCII.decode(prot.output))) 
    prot.send.onNext(Ident("Apollo 13"))
    prot.send.onNext(Hello("Houston"))
    prot.send.onNext(Chat("Apollo 13", "Houston", "We've had a problem."))
    lines.length should be (3)
    lines.dequeue should be ("IDENT~Apollo 13")
    lines.dequeue should be ("HELLO~Houston")
    lines.dequeue should be ("CHAT~Apollo 13~Houston~We've had a problem.")
  }
}