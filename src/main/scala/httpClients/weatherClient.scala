package httpClients

import play.api.libs.json.{JsValue, Json}
import scalaj.http._

object weatherClient {

  def main(args: Array[String]): Unit = {

    val woeid = 774508
    val format = "json"
    val proxyHost = "proxycorporativo"
    val proxyPort = 8080

    val request: HttpRequest = Http("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20woeid%3D"+woeid+")&format="+format+"&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys")
      .proxy(proxyHost, proxyPort)
      .timeout(connTimeoutMs = 2000, readTimeoutMs = 5000)

    val response: String = request.asString.body

    //print(response)

    val jsonObject: JsValue = Json.parse(response)

    val login = jsonObject \ "query"

    print(login)









  }

}
