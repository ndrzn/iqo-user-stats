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

  def find(userId: Int) : Future[Option[AppUser]]

  def find(name: String) : Future[Option[AppUser]]
}
