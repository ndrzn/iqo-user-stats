import java.util.Date

import Main.appArgs
import com.google.inject.Guice
import db.{AppUser, UserLogInfo}
import db.impl.{UserCacheRepository, UserLogCacheRepository}
import org.scalatest.{AsyncFlatSpec, FlatSpec, Matchers}
import utils.InMemory

import scala.concurrent.duration._
import scala.concurrent.Await
import scala.util.Success

/**
  * Created by Andrei Zubrilin, 2018
  */
class UserCacheRepositorySpec extends AsyncFlatSpec with Matchers {

  val injector = Guice.createInjector(new StartupModule(List(InMemory)))

  val users = injector.getInstance(classOf[UserCacheRepository])
  val userLogs = injector.getInstance(classOf[UserLogCacheRepository])

  var u = AppUser(-1, "a")

  "User cache repository " should "create users and read from cache" in {

    u = Await.result(users.create(u), 5.seconds)

    u.id should be >= 0

    users.find("a") map { r => r shouldBe defined }
  }

  "User logs repository" should "create logs and filter user records" in {
    import db.utils.TimestampHelper._
    var l = UserLogInfo(-1, u.id, "", "", new Date)

    l = Await.result(userLogs.create(l), 5.seconds)

    l.id should be >= 0

    userLogs.find(u.id) map (r => r.length should be > 0)

    userLogs.uniqueStat() map(r => r.length should be > 0)
  }


}
