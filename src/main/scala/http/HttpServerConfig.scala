package http

/**
  * Created by Andrei Zubrilin, 2018
  */
trait HttpServerConfig {

  val port: Int
  val interface: String

  val restartPolicy: RestartPolicy
}
