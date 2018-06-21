package http.controllers

import java.util.Date

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.RouteResult.Complete
import com.typesafe.scalalogging.LazyLogging
import http.Controller

import scala.concurrent.Future

/**
  * Created by Andrei Zubrilin, 2018
  */

/**
  * Handling invalid server routes
  */
class ErrorController extends Controller with LazyLogging{
  override def route: Route = {implicit ctx =>
      import http.utils.HeaderExtractor._

      logger.info(s"[${new Date()}] Illegal route access =>  [ip:$ip path:${ctx.request.uri.path}] $userAgent")

      Future.successful(Complete(HttpResponse(NotFound, entity =  "The requested page could not be found.")))
  }
}
