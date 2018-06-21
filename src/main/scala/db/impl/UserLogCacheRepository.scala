package db.impl

import java.util.Date

import com.google.inject.Inject
import com.typesafe.scalalogging.LazyLogging
import db.cache.CacheProxy
import db.{AppUser, UserLogInfo, UserLogRepository, UserRepository}
import org.cache2k.Cache

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}

/**
  * Created by Andrei Zubrilin, 2018
  */
class UserLogCacheRepository @Inject()(users: UserCacheRepository, cache: CacheProxy) extends UserLogRepository with LazyLogging {

  val prefix = "ul"

  override def create(record: UserLogInfo): Future[UserLogInfo] = {
    val log = record.copy(id = (new Date().getTime % Int.MaxValue).toInt)
    findByUser(log.userId).flatMap(logs =>
      cache.set(
        prefix + log.userId,
        log +: logs
      ).map { _ => log }
    )
  }

  override def findByUser(userId: Int, limit: Int = 20): Future[Seq[UserLogInfo]] =
    cache.get[UserLogInfo](prefix + userId).map(_.getOrElse(Nil))

  override def uniqueStat(): Future[Seq[UserLogInfo]] = {
    users.findAll().flatMap { s =>
      Future.sequence(s.map(u =>
        findByUser(u.id).map(_.headOption)
      )).map(_.flatten)
    }
  }
}
