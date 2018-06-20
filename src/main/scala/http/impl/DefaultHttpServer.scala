package http.impl

import akka.http.scaladsl.server.Route
import com.typesafe.scalalogging.LazyLogging
import http.{Controller, HttpServer, HttpServerConfig, KeepAlive}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server._
import Directives._
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.RouteResult.Complete
import akka.stream.ActorMaterializer

import scala.util.{Failure, Success}

/**
  * Created by Andrei Zubrilin, 2018
  */
class DefaultHttpServer(httpServerConfig: HttpServerConfig, routes: Controller*) extends HttpServer with LazyLogging {
  self =>

  import httpServerConfig._

  implicit val system = ActorSystem("http")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatchers.lookup("custom-dispatcher")

  def route() = routes.foldRight((new ErrorController).route)((a, b) => a.route ~ b)


  def checkRestartPolicy(): Any = restartPolicy match {
    case KeepAlive =>
      logger.info("Server restart scheduled")
      system.scheduler.scheduleOnce(restartTimeout)(run())
    case _ => system.terminate()
  }

  def run(): Unit = {

    logger.info(s"Binding server on $interface:$port...")

    val binding = Http().bindAndHandle(route(), interface, port)

    binding onComplete {
      case Success(s) => logger.info("Server is online")
      case Failure(e) => logger.error(e.getStackTraceString)
        checkRestartPolicy()
    }
  }
}
