package db.impl

import java.util.Date

import db.{AppUser, UserLogInfo, UserLogRepository}
import db.utils.TimestampHelper._
import javax.inject.Inject
import slick.model.ForeignKeyAction
import slick.driver.JdbcProfile
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

/**
  * Created by Andrei Zubrilin, 2018
  */
class PSQLUserLogRepository @Inject()(db : Database, val users: PSQLUserRepository) extends UserLogRepository {

  import slick.jdbc.{GetResult => GR}

  implicit def GetResultUserLogInfo(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp]): GR[UserLogInfo] = GR{
    prs => import prs._
      UserLogInfo.tupled((<<[Int], <<[Int], <<[String], <<[String], <<[java.sql.Timestamp]))
  }
  /** Table description of table user_logs. Objects of this class serve as prototypes for rows in queries. */
  class UserLogs(_tableTag: Tag) extends Table[UserLogInfo](_tableTag, "user_logs") {
    def * = (id, userId, userAgent, ip, loggedAt) <> (UserLogInfo.tupled, UserLogInfo.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(userId), Rep.Some(userAgent), Rep.Some(ip), Rep.Some(loggedAt)).shaped.<>({r=>import r._; _1.map(_=> UserLogInfo.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column user_id SqlType(int4) */
    val userId: Rep[Int] = column[Int]("user_id")
    /** Database column user_agent SqlType(varchar), Length(255,true) */
    val userAgent: Rep[String] = column[String]("user_agent", O.Length(255,varying=true))
    /** Database column ip SqlType(varchar), Length(32,true) */
    val ip: Rep[String] = column[String]("ip", O.Length(32,varying=true))
    /** Database column logged_at SqlType(timestamp) */
    val loggedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("logged_at")

    /** Foreign key referencing AppUsers (database name user_logs_app_users_id_fk) */
    lazy val appUsersFk = foreignKey("user_logs_app_users_id_fk", userId, users.appUsers)(r => r.id, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table UserLogs */
  lazy val userLogs = new TableQuery(tag => new UserLogs(tag))


  override def create(record: UserLogInfo): Future[UserLogInfo] = Future.successful(UserLogInfo(-1,-1,"","", new Date))

  override def find(userId: Int): Future[Seq[UserLogInfo]] = Future.successful(Nil)

  override def uniqueStat(): Future[Seq[UserLogInfo]] = Future.successful(Seq(UserLogInfo(-1,-1,"","", new Date)))

  override def detailedStat(user: AppUser, limit: Int): Future[Seq[UserLogInfo]] = Future.successful(Nil)
}
