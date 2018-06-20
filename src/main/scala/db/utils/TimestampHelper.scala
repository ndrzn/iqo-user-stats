package db.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date

import play.api.libs.json._

/**
  * Created by Andrei Zubrilin, 2018
  */
object TimestampHelper {

  implicit val timestampFormat: Format[Timestamp] = new Format[Timestamp]{
    val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
    def writes(t: Timestamp): JsValue = JsString(format.format(t))

    def reads(json: JsValue): JsResult[Timestamp] = JsSuccess(new Timestamp(format.parse(json.as[String]).getTime))
  }

  implicit def ts(date: Date): Timestamp = new Timestamp(date.getTime)
}
