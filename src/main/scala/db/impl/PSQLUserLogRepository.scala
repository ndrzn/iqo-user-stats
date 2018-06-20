package db.impl

import java.util.Date

import db.{AppUser, UserLogInfo, UserLogRepository}
import db.utils.TimestampHelper._
import scala.concurrent.Future

/**
  * Created by Andrei Zubrilin, 2018
  */
class PSQLUserLogRepository extends UserLogRepository {

  override def create(record: UserLogInfo): Future[UserLogInfo] = Future.successful(UserLogInfo(-1,-1,"","", new Date))

  override def find(userId: Int): Future[Seq[UserLogInfo]] = Future.successful(Nil)

  override def uniqueStat(): Future[Seq[UserLogInfo]] = Future.successful(Seq(UserLogInfo(-1,-1,"","", new Date)))

  override def detailedStat(user: AppUser, limit: Int): Future[Seq[UserLogInfo]] = Future.successful(Nil)
}
