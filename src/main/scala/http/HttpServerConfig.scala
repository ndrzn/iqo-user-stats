package http

import com.google.inject.ImplementedBy
import http.impl.DefaultHttpConfig

import scala.concurrent.duration.{Duration, FiniteDuration}

/**
  * Created by Andrei Zubrilin, 2018
  */
@ImplementedBy(classOf[DefaultHttpConfig])
trait HttpServerConfig {

  val port: Int

  val interface: String

  // Keep server alive on failure or terminate
  val restartPolicy: RestartPolicy

  val restartTimeout : FiniteDuration
}
