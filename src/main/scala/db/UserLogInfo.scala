package db

import java.sql.Timestamp

import play.api.libs.json.{Format, Json}

/**
  * Created by Andrei Zubrilin, 2018
  */
case class UserLogInfo (id: Int, userId: Int, userAgent: String, ip: String, loggedAt: Timestamp)

object UserLogInfo {

  import db.utils.TimestampHelper._

  implicit val appUserFormat: Format[UserLogInfo] = Json.format[UserLogInfo]
}