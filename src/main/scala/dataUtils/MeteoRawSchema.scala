package dataUtils

import schemaModels._
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class MeteoRawSchema(units: Units, title: String, link: String, description:String, language: String,
                          lastBuildDate:String, ttl: String, location: Location, wind: WindRaw, atmosphere: AtmosphereRaw, astronomy: Astronomy, image: Image, item: ItemRaw) extends Serializable


//Raw objects
/*
object AtmosphereRaw{
  def parse(atm: AtmosphereRaw): Atmosphere ={
    new Atmosphere(atm.humidity.toDouble, atm.pressure.toDouble, atm.rising.toDouble, atm.visibility.toDouble)
  }
}

object ForecastRaw{
  def parse(fcastRaw: ForecastRaw) ={
    new Forecast(fcastRaw.code.toInt, fcastRaw.date, fcastRaw.day, fcastRaw.high.toDouble, fcastRaw.low.toDouble, fcastRaw.text)
  }
}
*/

object MeteoRawSchema {

  implicit val unitsReads: Reads[Units] = (
    (JsPath \ "distance").read[String] and
      (JsPath \ "pressure").read[String] and
      (JsPath \ "speed").read[String] and
      (JsPath \ "temperature").read[String]
    )(Units.apply _)

  implicit val locationReads: Reads[Location] = (
    (JsPath \ "city").read[String] and
      (JsPath \ "country").read[String] and
      (JsPath \ "region").read[String]
    )(Location.apply _)

  implicit val windReads: Reads[WindRaw] = (
    (JsPath \ "chill").read[String] and
      (JsPath \ "direction").read[String] and
      (JsPath \ "speed").read[String]
    )(WindRaw.apply _)

  implicit val atmosphereReads: Reads[AtmosphereRaw] = (
    (JsPath \ "humidity").read[String] and
      (JsPath \ "pressure").read[String] and
      (JsPath \ "rising").read[String] and
      (JsPath \ "visibility").read[String]
    )(AtmosphereRaw.apply _)

  implicit val astronomyReads: Reads[Astronomy] = (
    (JsPath \ "sunrise").read[String] and
      (JsPath \ "sunset").read[String]
    )(Astronomy.apply _)

  implicit val imageReads: Reads[Image] = (
    (JsPath \ "title").read[String] and
      (JsPath \ "width").read[String] and
      (JsPath \ "height").read[String] and
      (JsPath \ "link").read[String] and
      (JsPath \ "url").read[String]
    )(Image.apply _)

  implicit val forecastReads: Reads[ForecastRaw] = (
    (JsPath \ "code").read[String] and
      (JsPath \ "date").read[String] and
      (JsPath \ "day").read[String] and
      (JsPath \ "high").read[String] and
      (JsPath \ "low").read[String] and
      (JsPath \ "text").read[String]
    )(ForecastRaw.apply _)

  implicit val itemReads: Reads[ItemRaw] = (
    (JsPath \ "title").read[String] and
      (JsPath \ "lat").read[String] and
      (JsPath \ "long").read[String] and
      (JsPath \ "link").read[String] and
      (JsPath \ "pubDate").read[String] and
      (JsPath \ "condition").read[ConditionRaw] and
      (JsPath \ "forecast").read[Seq[ForecastRaw]] and
      (JsPath \ "description").read[String] and
      (JsPath \ "guid").read[String]
    )(ItemRaw.apply _)

  implicit val conditionReads: Reads[ConditionRaw] = (
    (JsPath \ "code").read[String] and
      (JsPath \ "date").read[String] and
      (JsPath \ "temp").read[String] and
      (JsPath \ "text").read[String]
    )(ConditionRaw.apply _)

  implicit val MeteoReads: Reads[MeteoRawSchema] = (
    (JsPath \ "units").read[Units] and
      (JsPath \ "title").read[String] and
      (JsPath \ "link").read[String] and
      (JsPath \ "description").read[String] and
      (JsPath \ "language").read[String] and
      (JsPath \ "lastBuildDate").read[String] and
      (JsPath \ "ttl").read[String] and
      (JsPath \ "location").read[Location] and
      (JsPath \ "wind").read[WindRaw] and
      (JsPath \ "atmosphere").read[AtmosphereRaw] and
      (JsPath \ "astronomy").read[Astronomy] and
      (JsPath \ "image").read[Image] and
      (JsPath \ "item").read[ItemRaw]
    ) (MeteoRawSchema.apply _)

  def parse(jsValue: JsValue) {

    jsValue.validate[MeteoRawSchema] match {
      case s: JsSuccess[MeteoRawSchema] => {
        println("OK")
      }
      case e: JsError => {
        println("Error parseando")
      }
    }
  }

}
