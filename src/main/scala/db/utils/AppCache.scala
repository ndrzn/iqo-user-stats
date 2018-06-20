package db.utils

import java.util

import com.typesafe.scalalogging.LazyLogging
import org.cache2k.Cache

import scala.collection.JavaConverters._
import scala.concurrent.Future
import scala.util.Try
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Andrei Zubrilin, 2018
  */
class AppCache(cache: Cache[String, util.List[Object]]) extends LazyLogging {

  def getF[T](key: String): Future[Option[Seq[T]]] = Future {
    Try(cache.get(key)
    ).toOption match {
      case Some(null) => None
      case x => x
    }
  }.map(_.map(_.asScala.asInstanceOf[Seq[T]]))


  def setF(key: String, seq: Seq[Object]) = Future {
    cache.put(key, seq.asJava)
  }
}
