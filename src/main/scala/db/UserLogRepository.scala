package db

import com.google.inject.ImplementedBy
import db.impl.PSQLUserLogRepository

import scala.concurrent.Future

/**
  * Created by Andrei Zubrilin, 2018
  */
@ImplementedBy(classOf[PSQLUserLogRepository])
trait UserLogRepository {

  def create(record: UserLogInfo) : Future[UserLogInfo]

  def find(userId : Int) : Future[Seq[UserLogInfo]]

  def uniqueStat(): Future[Seq[UserLogInfo]]

  def detailedStat(user: AppUser, limit: Int = 20): Future[Seq[UserLogInfo]]
}
