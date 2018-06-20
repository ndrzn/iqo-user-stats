package db

import java.util
import java.util.concurrent.TimeUnit
import scala.collection.JavaConverters._
import db.utils.AppCache
import org.cache2k.{Cache, Cache2kBuilder}

/**
  * Created by Andrei Zubrilin, 2018
  */
package object impl {
  lazy val cache = new Cache2kBuilder[String, util.List[Object]](){}
    .expireAfterWrite(5, TimeUnit.MINUTES)
    .entryCapacity(20000)
    .build()

  implicit def appCache(cache: Cache[String, util.List[Object]]) = new AppCache(cache)
}
