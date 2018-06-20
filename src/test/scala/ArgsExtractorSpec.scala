import org.scalatest.{FlatSpec, Matchers}
import utils.{ArgsExtractor, InMemory, Port}

/**
  * Created by Andrei Zubrilin, 2018
  */
class ArgsExtractorSpec extends FlatSpec with Matchers {

  val goodArgs = Array("-p", "1234", "--in-memory")
  val mixedArgs = Array("-p", "--in-memory", "1234")
  val badArgs = Array("-memo", "q", "-p")

  "ArgsExtractor" should "correctly parse input" in {

    ArgsExtractor(goodArgs) should equal(List(Port(1234), InMemory))

    ArgsExtractor(mixedArgs) should have length 1

    ArgsExtractor(badArgs) shouldBe Nil
  }

}
