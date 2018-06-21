package db

import com.google.inject.ImplementedBy
import db.impl.PSQLUserLogRepository

import scala.concurrent.Future

/**
  * Created by Andrei Zubrilin, 2018
  */
@ImplementedBy(classOf[PSQLUserLogRepository])
trait UserLogRepository {

  /**
    * Create new UserLogInfo record
    *
    * @param record
    * @return
    */
  def create(record: UserLogInfo) : Future[UserLogInfo]

  /**
    * Get list of user logs by user id
    *
    * @param userId
    * @param limit
    * @return
    */
  def findByUser(userId : Int, limit: Int = 20) : Future[Seq[UserLogInfo]]

  /**
    * Get unique user entries (the newest ones)
    *
    * @return
    */
  def uniqueStat(): Future[Seq[UserLogInfo]]
}
