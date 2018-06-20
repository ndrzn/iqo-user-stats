package db.impl

import db.{AppUser, UserLogInfo, UserRepository}

import scala.concurrent.Future

/**
  * Created by Andrei Zubrilin, 2018
  */
class PSQLUserRepository extends UserRepository{


  override def create(newUser: AppUser): Future[AppUser] = Future.successful(AppUser(1, ""))

  override def findUser(userId: Int): Future[AppUser] = Future.successful(AppUser(1, ""))

  override def uniqueStat(): Future[Seq[UserLogInfo]] = Future.successful(Seq())

  override def detailedStat(user: AppUser): Future[Seq[UserLogInfo]] = Future.successful(Seq())
}
