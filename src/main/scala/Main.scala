import akka.actor.ActorSystem
import com.google.inject.Guice
import http.HttpServerConfig
import utils.{AppArgs, ArgsExtractor, Port}
import com.google.inject.name.Names
import com.sun.deploy.net.HttpResponse
import http.controllers.UserController
import http.impl.{DefaultHttpServer, HttpConfigArgsDecorator}

/**
  * Created by Andrei Zubrilin, 2018
  */
object Main extends App {

  //Parse command line
  implicit val appArgs: Seq[AppArgs] = ArgsExtractor(args)

  //Read configs and init services
  val injector = Guice.createInjector(new StartupModule(appArgs))

  val defaultConfig = injector.getInstance(classOf[HttpServerConfig])

  val userController = injector.getInstance(classOf[UserController])

  val httpConfig = new HttpConfigArgsDecorator(defaultConfig)

  //Start http server
  val server = new DefaultHttpServer(httpConfig, userController)

  server.run()
}
