package db.impl

import db.{AppUser, UserLogInfo, UserRepository}

import scala.concurrent.Future

/**
  * Created by Andrei Zubrilin, 2018
  */
class PSQLUserRepository extends UserRepository{


  override def create(newUser: AppUser): Future[AppUser] = Future.successful(AppUser(1, ""))

  override def find(userId: Int): Future[Option[AppUser]] = Future.successful(Some(AppUser(1, "")))

  override def find(name: String): Future[Option[AppUser]] = Future.successful(Some(AppUser(1, "")))

}
