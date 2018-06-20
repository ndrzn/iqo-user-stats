package db.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat

import play.api.libs.json._

/**
  * Created by Andrei Zubrilin, 2018
  */
object TimestampConverter {

  implicit val timestampFormat = new Format[Timestamp]{
    val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
    def writes(t: Timestamp): JsValue = JsString(format.format(t))

    def reads(json: JsValue): JsResult[Timestamp] = JsSuccess(new Timestamp(format.parse(json.as[String]).getTime))
  }
}
