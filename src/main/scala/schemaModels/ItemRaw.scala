package schemaModels

case class ItemRaw(title: String, lat: String, long: String, link: String, pubDate: String, condition: ConditionRaw, forecast: Seq[ForecastRaw], description: String, guid: String) extends Serializable