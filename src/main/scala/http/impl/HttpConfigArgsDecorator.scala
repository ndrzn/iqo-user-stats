package http.impl

import http.HttpServerConfig
import utils.{AppArgs, Port}

import scala.concurrent.duration.FiniteDuration

/**
  * Created by Andrei Zubrilin, 2018
  */
class HttpConfigArgsDecorator (config: HttpServerConfig)(implicit args: Seq[AppArgs]) extends HttpServerConfig{

  override val port: Int = args.collectFirst{case p @ Port(_) => p.v}.getOrElse(config.port)
  override val interface: String = config.interface
  override val restartPolicy: http.RestartPolicy = config.restartPolicy
  override val restartTimeout: FiniteDuration = config.restartTimeout
}
