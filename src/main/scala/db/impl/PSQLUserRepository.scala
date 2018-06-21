package db.impl

import db.{AppUser, UserLogInfo, UserRepository}
import javax.inject.Inject
import slick.driver.JdbcProfile
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

/**
  * Created by Andrei Zubrilin, 2018
  */
class PSQLUserRepository @Inject()(db: Database) extends UserRepository {

  import slick.jdbc.{GetResult => GR}

  implicit def GetResultAppUsersRow(implicit e0: GR[Int], e1: GR[String]): GR[AppUser] = GR {
    prs =>
      import prs._
      AppUser.tupled((<<[Int], <<[String]))
  }

  class AppUsers(_tableTag: Tag) extends Table[AppUser](_tableTag, "app_users") {
    def * = (id, name) <> (AppUser.tupled, AppUser.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name)).shaped.<>({ r => import r._; _1.map(_ => AppUser.tupled((_1.get, _2.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(varchar), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255, varying = true))

    /** Uniqueness Index over (name) (database name app_users_name_uindex) */
    val index1 = index("app_users_name_uindex", name, unique = true)
  }

  /** Collection-like TableQuery object for table AppUsers */
  lazy val appUsers = new TableQuery(tag => new AppUsers(tag))


  override def create(newUser: AppUser): Future[AppUser] = {
    val action = (appUsers.map(_.name) returning appUsers.map(_.id)) += newUser.name

    db.run(action).map(id => newUser.copy(id = id))
  }

  override def find(userId: Int): Future[Option[AppUser]] = {
    db.run(appUsers.filter(_.id === userId).result.headOption)
  }

  override def find(name: String): Future[Option[AppUser]] = {
    db.run(appUsers.filter(_.name.toLowerCase === name.toLowerCase).result.headOption)
  }

}
