package http.impl

import java.util.Date

import akka.http.scaladsl.model._
import db.UserRepository
import http.Controller
import javax.inject.Inject
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.server.{RequestContext, Route, RouteResult}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server._
import Directives._
import akka.http.scaladsl.server.RouteResult.Complete
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Future

/**
  * Created by Andrei Zubrilin, 2018
  */
class ErrorController extends Controller with LazyLogging{
  override def route: Route = {implicit ctx =>
      import http.utils.HeaderExtractor._

      logger.info(s"[${new Date()}] Illegal route access =>  $ip : ${ctx.request.uri.path}, $userAgent")

      Future.successful(Complete(HttpResponse(NotFound, entity =  "The requested page could not be found.")))
  }
}
