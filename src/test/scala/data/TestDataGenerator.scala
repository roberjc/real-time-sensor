package data

import dataUtils.DataGenerator._
import play.api.libs.json.Json

object TestDataGenerator {
  def main(args: Array[String]) {

    val deviceId = genRandDeviceId(5)
    val deviceData = buildDeviceData(deviceId, 20)

    println(Json.prettyPrint(deviceData))
  }
}
