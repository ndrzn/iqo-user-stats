package http.impl

import com.google.inject.Inject
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import http.{HttpServerConfig, KeepAlive, Terminate}
import utils.Port

import scala.concurrent.duration._
import scala.util.Try

/**
  * Created by Andrei Zubrilin, 2018
  */
class DefaultHttpConfig @Inject()(config: Config) extends HttpServerConfig with LazyLogging {

  val httpConfig = Try{
    val c = config.getConfig("app.http")
    logger.info("Http config successfully loaded.")
    c
  }.getOrElse{
    logger.info("Http config not found, using default..")
    config
  }

  val defaultPort = 7979

  val defaultInterface = "0.0.0.0"

  val interfaceRegex = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"

  val defaultTimeout: FiniteDuration = 10 seconds

  override val port: Int = Try(httpConfig.getInt("port")).toOption.getOrElse(defaultPort)

  override val interface: String = httpConfig.getString("interface") match {
    case s if s.matches(interfaceRegex) => s
    case _ => defaultInterface
  }

  override val restartPolicy: http.RestartPolicy = httpConfig.getString("restart.policy") match {
    case s if s == "keep-alive" => KeepAlive
    case _ => Terminate
  }

  override val restartTimeout: FiniteDuration = Try(DurationInt(httpConfig.getInt("restart.timeout")).seconds).toOption.getOrElse(defaultTimeout)
}
