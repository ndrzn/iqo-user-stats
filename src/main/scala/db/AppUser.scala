package db

import play.api.libs.json.{Format, Json, Reads, Writes}

/**
  * Created by Andrei Zubrilin, 2018
  */
case class AppUser(id: Int, name: String)

object AppUser{
  implicit val appUserFormat: Format[AppUser] = Json.format[AppUser]

  def tupled = (AppUser.apply _).tupled
}