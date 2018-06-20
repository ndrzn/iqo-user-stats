package http.impl

import akka.http.scaladsl.model._
import db.UserRepository
import http.Controller
import javax.inject.Inject
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.server.{RequestContext, Route, RouteResult}
import akka.http.scaladsl.server.Directives._

import scala.concurrent.Future

/**
  * Created by Andrei Zubrilin, 2018
  */
class UserController @Inject()(users: UserRepository) extends Controller {
  override def route: Route =
  //Task1 unique stat
    path("stat") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, ""))
      }
    } ~
      path("user") {
        //Additional detailed stat by user id
        path(IntNumber) {
          userId =>
            get {
              complete("")
            }
        } ~
        //Task2 collect user visits
          post {
            complete("")
          }
      }
}
