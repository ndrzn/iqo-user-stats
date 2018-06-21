package http.controllers

import java.util.Date

import akka.http.scaladsl.model.{MediaType, MediaTypes, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{RequestContext, Route}
import com.typesafe.scalalogging.LazyLogging
import db.{AppUser, UserLogInfo, UserLogRepository, UserRepository}
import http.Controller
import javax.inject.Inject
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Try

/**
  * Created by Andrei Zubrilin, 2018
  */
class UserController @Inject()(users: UserRepository, userLogs: UserLogRepository) extends Controller with LazyLogging {

  override def route: Route =
  //Task1 unique stat
    path("stat") {
      get {
        onSuccess(userLogs.uniqueStat()) { r =>
          complete(r.length.toString)
        }
      }
    } ~
      //Additional detailed stat by user id
      path("user" / IntNumber) {
        userId =>
          get {
            onSuccess(userLogs.findByUser(userId)) { r =>
              import db.UserLogInfo._
              complete(Json.toJson(r).toString())
            }
          }
      } ~
      //Task2 collect user data
      path("user") {
        post {
          decodeRequest {
            entity(as[String]) { s =>
              extractRequestContext { implicit ctx =>

                val response = for{
                  _ <- validateContentType(MediaTypes.`application/json`)
                  user <- validateUserData(s)
                } yield proceedUser(user)

                response match {
                  case Left(msg) => complete(StatusCodes.BadRequest, msg.toString)
                  case _ => complete("Ok")
                }
              }
            }
          }
        }
      }

  def validateContentType(expect: MediaType)(implicit ctx: RequestContext) = {
    import http.utils.HeaderExtractor._
    contentType match {
      case c if c == expect => Right(c)
      case _ => Left(s"Bad content type, $expect expected.")
    }
  }

  def validateUserData(s: String) = {
    Try {
      val json = Json.parse(s)

      val name = (json \ "user_id").as[String]
      AppUser(name = name)
    }.toEither match {
        //Don't let redundant data be shown to end-user
      case Left(_) => Left("Bad json string.")
      case r => r
    }
  }

  /**
    * Check if user already exist in our system and register his entry
    *
    * @param user
    * @param ctx
    */
  def proceedUser(user: AppUser)(implicit ctx: RequestContext): Unit = {
    users.find(user.name).flatMap { u =>
      if (u.isEmpty) users.create(user) else Future.successful(u.get)
    }.foreach { u =>
      import db.utils.TimestampHelper._
      import http.utils.HeaderExtractor._

      userLogs.create(UserLogInfo(-1, u.id, userAgent, ip.toString(), new Date()))
    }
  }

}
