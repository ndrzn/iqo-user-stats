
import java.util.concurrent.TimeUnit

import com.google.inject.AbstractModule
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging
import db.cache.CacheProxy
import db.{AppUser, UserLogInfo, UserLogRepository, UserRepository}
import db.impl.{UserCacheRepository, UserLogCacheRepository}
import http.HttpServerConfig
import org.cache2k.{Cache, Cache2kBuilder}
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.duration._
import utils.{AppArgs, InMemory}

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Andrei Zubrilin, 2018
  */
class StartupModule(appArgs: Seq[AppArgs]) extends AbstractModule {

  override def configure(): Unit = {

    //Global config
    val config = ConfigFactory.load()
    bind(classOf[Config]).toInstance(config)

    //Repository bindings
    if(appArgs.contains(InMemory)) {
      bind(classOf[CacheProxy]).asEagerSingleton()
      bind(classOf[UserRepository]).to(classOf[UserCacheRepository])
      bind(classOf[UserLogRepository]).to(classOf[UserLogCacheRepository])
    }
    else {
      val db = Database.forConfig("slick")
      bind(classOf[PostgresProfile.backend.Database]).toInstance(db)
    }
  }
}
