package dataUtils

import java.text.SimpleDateFormat
import java.util.Calendar

import play.api.libs.json.{JsNumber, JsObject, JsString, JsValue}

import scala.util.Random

object DataGenerator {

  def generateRandom(numberDevices: Int): Long = {
    val random = new scala.util.Random
    var res = random.nextInt(numberDevices).toLong
    if(res == 0)
      res = generateRandom(numberDevices)
    return res
  }

  private def generateTemp(deviceId: Long = 0L, hour: Int): Double = {
    //Set a seed based random number and then a new free random
    // with a variation of 2 degrees
    val seedRandom = new scala.util.Random(deviceId)
    val baseRandom = new scala.util.Random

    val hoursByTemp = Map(10 -> List(1, 2, 3, 4, 5, 6, 7, 8, 9),
      20 -> List(10, 11, 21, 22, 23, 0),
      30 -> List(12, 13, 14, 15, 19, 20),
      40 -> List(16, 17, 18))

    val base = hoursByTemp.find(_._2.contains(hour)).get._1

    val res = f"${base + seedRandom.nextInt(10) + baseRandom.nextDouble}%.1f".replace(',', '.').toDouble

    return res
  }

  private def generateHum(temp: Double): Int ={
    val baseRandom = new Random(Math.round(temp))
    val res = baseRandom.nextInt(100 / 5)*5

    return res
  }

  /**
    *
    * @param deviceId device for which the temp is generated
    * @param hour     hour in the day when the temp is generated
    * @return device data in JSON format
    */
  def buildDeviceData(sectorId: Long, deviceId: Long, hour: Int): JsValue = {
    val temp = generateTemp(sectorId, hour)
    val hum = generateHum(temp)
    val dateFormat = "dd-MM-yyyy HH:mm:ss"
    val sdf = new SimpleDateFormat(dateFormat)

    val res: JsValue = JsObject(
      Seq(
        "sector_id" -> JsNumber(sectorId),
        "device_id" -> JsNumber(deviceId),
        "event_time" -> JsString(sdf.format(Calendar.getInstance().getTime)),
        "measures" -> JsObject(
          Seq(
            "temperature" -> JsNumber(temp),
            "humidity" -> JsNumber(hum)
          )
        )
      ))
    return res
  }
}
