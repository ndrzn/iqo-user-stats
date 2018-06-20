package utils

import scala.annotation.tailrec
import scala.util.Try

/**
  * Created by Andrei Zubrilin, 2018
  */
sealed trait AppArgs

case class Port(v: Int) extends AppArgs

case object InMemory extends AppArgs

object ArgsExtractor {

  def validatePort(p: String): Option[Port] = Try(p.toInt).toOption.map(Port)

  def apply(args: Array[String]): Seq[AppArgs] = {

    @tailrec
    def extract(arr: List[String], l: List[AppArgs] = Nil): List[AppArgs] = arr match {
      case Nil => l
      case x :: xs if x == "--in-memory" => extract(xs, InMemory :: l)
      case x :: y :: xs if x == "-p" => {
        val port = validatePort(y)
        if (port.isDefined)
          extract(xs, port.get :: l)
        else
          extract(y :: xs, l)
      }
      case _ :: xs => extract(xs, l)
    }

    extract(args.toList)
  }
}