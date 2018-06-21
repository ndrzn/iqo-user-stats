package db.cache

import java.util
import java.util.concurrent.TimeUnit

import com.typesafe.scalalogging.LazyLogging
import javax.inject.Inject
import org.cache2k.Cache2kBuilder

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Try

/**
  * Created by Andrei Zubrilin, 2018
  */
class CacheProxy @Inject()(config: CacheConfig) {

  import config._

  //Due to the bad back and forth java type conversion keep stick with List[Object] instead of abstract [_]
  private val cache =  new Cache2kBuilder[String, util.List[Object]](){}
    .expireAfterWrite(expireAfter.toMinutes, TimeUnit.MINUTES)
    .entryCapacity(maxCapacity)
    .build()

  def get[T](key: String): Future[Option[Seq[T]]] = Future {
    Option(cache.get(key))
  }.map(_.map(_.asScala.asInstanceOf[Seq[T]]))


  def set(key: String, seq: Seq[Object]) = Future {
    cache.put(key, seq.asJava)
  }
}
