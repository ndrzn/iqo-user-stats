import akka.http.javadsl.server.RequestContext
import akka.http.scaladsl.model.{HttpEntity, HttpRequest, HttpResponse, ResponseEntity}
import akka.http.scaladsl.server.{Route, RouteResult}

import scala.concurrent.Future

/**
  * Created by Andrei Zubrilin, 2018
  */
package object http {
  sealed trait RestartPolicy
  case object KeepAlive extends RestartPolicy
  case object Terminate extends RestartPolicy


  implicit def ControllerToRoute(c: Controller): Route = {
    c.route
  }
}
