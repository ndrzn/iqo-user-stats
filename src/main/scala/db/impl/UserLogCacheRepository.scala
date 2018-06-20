package db.impl

import java.util.Date

import com.google.inject.Inject
import com.typesafe.scalalogging.LazyLogging
import db.{AppUser, UserLogInfo, UserLogRepository, UserRepository}
import org.cache2k.Cache

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}

/**
  * Created by Andrei Zubrilin, 2018
  */
class UserLogCacheRepository @Inject()(users: UserCacheRepository) extends UserLogRepository with LazyLogging {

  val prefix = "ul"

  override def create(record: UserLogInfo): Future[UserLogInfo] = {
    val log = record.copy(id = (new Date().getTime % Int.MaxValue).toInt)
    find(log.userId).flatMap(logs =>
      cache.setF(
        prefix + log.userId,
        log +: logs
      ).map { _ => log }
    )
  }

  override def find(userId: Int): Future[Seq[UserLogInfo]] =
    cache.getF[UserLogInfo](prefix + userId).map(_.getOrElse(Nil))

  override def uniqueStat(): Future[Seq[UserLogInfo]] = {
    users.findAll().flatMap { s =>
      Future.sequence(s.map(u =>
        find(u.id).map(_.headOption)
      )).map(_.flatten)
    }
  }

  override def detailedStat(user: AppUser, limit: Int = 20): Future[Seq[UserLogInfo]] =
    find(user.id).map(_.take(limit))
}
