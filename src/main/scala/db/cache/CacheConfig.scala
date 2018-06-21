package db.cache

import com.google.inject.ImplementedBy
import db.cache.impl.SimpleCacheConfig

import scala.concurrent.duration.Duration

/**
  * Created by Andrei Zubrilin, 2018
  */
@ImplementedBy(classOf[SimpleCacheConfig])
trait CacheConfig {

  //How long to keep records after last write operation (in seconds)
  val expireAfter : Duration

  //Max number of elements on heap
  val maxCapacity : Int
}
