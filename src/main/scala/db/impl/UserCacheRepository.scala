package db.impl

import java.util.Date

import com.google.inject.Inject
import com.typesafe.scalalogging.LazyLogging
import db.{AppUser, UserLogInfo, UserRepository}
import org.cache2k.Cache

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by Andrei Zubrilin, 2018
  */
class UserCacheRepository extends UserRepository with LazyLogging {

  val prefix = "u"

  override def create(newUser: AppUser): Future[AppUser] = {
    val user = newUser.copy(id = (new Date().getTime % Int.MaxValue).toInt)
    findAll().flatMap {
      case l if l.exists(_.name.toLowerCase == user.name.toLowerCase) => Future.successful(user)
      case l => cache.setF(prefix, user +: l).map { _ => user }
    }
  }

  override def find(userId: Int): Future[Option[AppUser]] =
    cache.getF[AppUser](prefix).map(_.flatMap(_.find(_.id == userId)))


  def findAll(): Future[Seq[AppUser]] =
    cache.getF[AppUser](prefix).map(_.getOrElse(Nil))

  override def find(name: String): Future[Option[AppUser]] = {
      cache.getF[AppUser](prefix).map(
        _.flatMap(
          _.find(
            _.name.toLowerCase == name.toLowerCase)
        ))
    }

}