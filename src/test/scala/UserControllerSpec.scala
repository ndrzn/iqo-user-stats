import Main.injector
import akka.http.scaladsl.model.{ContentType, HttpEntity, MediaTypes, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.server._
import Directives._
import akka.http.javadsl.model.RequestEntity
import com.google.inject.Guice
import http.controllers.UserController
import org.scalatest.{FlatSpec, Matchers, WordSpec}
import utils.InMemory

/**
  * Created by Andrei Zubrilin, 2018
  */
class UserControllerSpec extends WordSpec with Matchers with ScalatestRouteTest{

  val injector = Guice.createInjector(new StartupModule(List(InMemory)))

  val userController = injector.getInstance(classOf[UserController])

  import userController._

  "User controller" should {

    "return Ok message on POST with valid json to /user" in {

      Post("/user", HttpEntity(ContentType(MediaTypes.`application/json`), """{"user_id": "user1"}""") ) ~> route ~> check {
        responseAs[String] shouldEqual "Ok"
      }
    }

    "return BadRequest on POST with invalid content type" in {

      Post("/user","""{"user_id": "user1"}""" ) ~> route ~> check {

        status shouldEqual StatusCodes.BadRequest

        responseAs[String] should include ("Bad content type")
      }
    }

    "return BadRequest on POST with invalid json" in {

      Post("/user", HttpEntity(ContentType(MediaTypes.`application/json`), """user_id : user1""") ) ~> route ~> check {

        status shouldEqual StatusCodes.BadRequest

        responseAs[String] should include ("Bad json string")
      }
    }
  }
}
