package http.impl

import akka.http.scaladsl.server.Route
import com.typesafe.scalalogging.LazyLogging
import http.{Controller, HttpServer, HttpServerConfig}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server._
import Directives._
import akka.http.scaladsl.server.RouteResult.Complete
/**
  * Created by Andrei Zubrilin, 2018
  */
class DefaultHttpServer (httpServerConfig: HttpServerConfig, routes: Controller*) extends HttpServer with LazyLogging{
  self =>

  def route() = routes.foldRight((new ErrorController).route)((a,b) => a.route ~ b)

  def run() = {

  }
}
