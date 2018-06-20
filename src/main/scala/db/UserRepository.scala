package db

import com.google.inject.ImplementedBy
import db.impl.PSQLUserRepository

import scala.concurrent.Future

/**
  * Created by Andrei Zubrilin, 2018
  */
@ImplementedBy(classOf[PSQLUserRepository])
trait UserRepository {

  def create(newUser: AppUser) : Future[AppUser]

  def findUser(userId: Int) : Future[AppUser]

  def uniqueStat() : Future[Seq[UserLogInfo]]

  def detailedStat(user: AppUser) : Future[Seq[UserLogInfo]]
}
