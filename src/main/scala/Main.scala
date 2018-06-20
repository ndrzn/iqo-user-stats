import akka.actor.ActorSystem
import com.google.inject.Guice
import http.HttpServerConfig
import utils.ArgsExtractor
import com.google.inject.name.Names
import http.impl.{DefaultHttpServer, UserController}

/**
  * Created by Andrei Zubrilin, 2018
  */
object Main extends App {

  val appArgs = ArgsExtractor(args)

  val injector = Guice.createInjector(new StartupModule(appArgs))

  val httpConfig = injector.getInstance(classOf[HttpServerConfig])

  val userController = injector.getInstance(classOf[UserController])

  val server = new DefaultHttpServer(httpConfig, userController)

  server.run()
}
