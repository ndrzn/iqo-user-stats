package http.utils

import akka.http.scaladsl.model.{HttpEntity, _}
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.server.RequestContext
import akka.http.scaladsl.server.directives.HeaderDirectives.headerValuePF
import com.typesafe.scalalogging.LazyLogging

/**
  * Created by Andrei Zubrilin, 2018
  */
/**
  * Simple helper for extracting various request header data
  * without converting into new route(akka approach)
  */
object HeaderExtractor extends LazyLogging{

  def contentType(implicit ctx: RequestContext): MediaType = {
    ctx.request.headers.collectFirst {
      case `Content-Type`(ct) => ct.mediaType
    }.getOrElse(
      ctx.request.entity match {
        case HttpEntity.Strict(ct, _) => ct.mediaType
        case _ => MediaTypes.`text/html`
      }
    )
  }

  def ip(implicit ctx: RequestContext): RemoteAddress = {
    ctx.request.headers.collectFirst {
      case `X-Forwarded-For`(Seq(address, _*)) ⇒ address
      case `Remote-Address`(address) ⇒ address
      case `X-Real-Ip`(address) ⇒ address
    }.getOrElse(RemoteAddress.Unknown)
  }

  def userAgent(implicit ctx: RequestContext): String = ctx.request.headers.collectFirst {
    case `User-Agent`(products: Seq[ProductVersion]) => products.map(_.toString()).mkString(", ")
  }.getOrElse("Unknown agent")
}
