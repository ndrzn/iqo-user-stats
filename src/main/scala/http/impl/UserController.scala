package http.impl

import java.util.Date

import akka.http.scaladsl.model._
import db.{AppUser, UserLogInfo, UserLogRepository, UserRepository}
import http.Controller
import javax.inject.Inject
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.server.{RequestContext, Route, RouteResult}
import akka.http.scaladsl.server.Directives._
import com.typesafe.scalalogging.LazyLogging
import http.utils.HeaderExtractor.{ip, userAgent}
import play.api.libs.json.{JsValue, Json}

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
            onSuccess(userLogs.find(userId)) { r =>
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
              val json = Try(Json.parse(s)).toOption.getOrElse(Json.toJson(""))

              val mbUser = (json \ "user_id").asOpt[String]
              extractRequestContext { implicit ctx =>
                validateAndLog(mbUser)
                complete("Ok")
              }
            }
          }
        }
      }

  def validateAndLog(mbUser: Option[String])(implicit ctx: RequestContext): Unit = {

    mbUser.foreach { name =>
      users.find(name).flatMap { u =>
        logger.debug(u.toString)
        if (u.isEmpty) users.create(AppUser(-1, name)) else Future.successful(u.get)
      }.foreach { u =>
        import http.utils.HeaderExtractor._
        import db.utils.TimestampHelper._

        userLogs.create(UserLogInfo(-1, u.id, userAgent, ip.toString(), new Date()))
      }
    }
  }

}
