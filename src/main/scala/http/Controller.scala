package http

import akka.http.scaladsl.server.Route

/**
  * Created by Andrei Zubrilin, 2018
  */
trait Controller {
  def route : Route
}
