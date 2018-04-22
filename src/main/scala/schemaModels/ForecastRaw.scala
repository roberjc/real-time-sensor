package schemaModels

case class ForecastRaw(code: String, date: String, day: String, high: String, low:String, text: String) extends Serializable