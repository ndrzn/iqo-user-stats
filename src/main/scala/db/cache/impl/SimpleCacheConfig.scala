package db.cache.impl

import com.google.inject.Inject
import com.typesafe.config.Config
import db.cache.CacheConfig
import scala.concurrent.duration
import scala.concurrent.duration.Duration
import scala.concurrent.duration._
import scala.util.Try

/**
  * Created by Andrei Zubrilin, 2018
  */
class SimpleCacheConfig @Inject()(config: Config) extends CacheConfig{

  val cacheConfig = Try(config.getConfig("app.cache")).getOrElse(config)

  val defaultExpireTime = 60 seconds

  val defaultCapacity = 200000

  override val expireAfter: Duration = Try(DurationInt(cacheConfig.getInt("expire-after")).seconds).toOption.getOrElse(defaultExpireTime)
  override val maxCapacity: Int =  Try(cacheConfig.getInt("max-capacity")).toOption.getOrElse(maxCapacity)
}
