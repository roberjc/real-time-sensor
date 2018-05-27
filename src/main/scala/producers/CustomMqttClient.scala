package producers

import java.util.Calendar

import dataUtils.DataGenerator.{buildDeviceData, generateRandom}
import org.eclipse.paho.client.mqttv3._
import play.api.libs.json.JsValue


object CustomMqttClient extends MqttCallback{

  val connectOptions = new MqttConnectOptions

  val isSubscriber = true
  val isPublisher = true

  /**
    * connectionLost
    * This callback is invoked upon losing the MQTT connection.
    */
  override def connectionLost(throwable: Throwable): Unit = {
    System.out.println("Conexión perdida")
  }

  /**
    * messageArrived
    * This callback is invoked when a message is received on a subscribed topic.
    */
  @throws[Exception]
  override def messageArrived(s: String, mqttMessage: MqttMessage): Unit = {
    System.out.println("Topic: " + s)
    System.out.println("Mensaje: " + mqttMessage)
  }

  /**
    * deliveryComplete
    * This callback is invoked when a message published by this client
    * is successfully received by the broker.
    */
  override def deliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken): Unit = {
    System.out.println("Mensaje recibido")
  }

  def setTopic(client: MqttClient, topic: String): MqttTopic = {
    val mqttTopic = client.getTopic(topic)
    mqttTopic
  }

  def setPublish(mqttTopic: MqttTopic): Unit = {
    if (isPublisher) {
      var i = 1
      while ( {
        i <= 500
      }) {
        val sectorId = generateRandom(20)
        val deviceId = generateRandom(2)
        val deviceData: JsValue = buildDeviceData(sectorId, deviceId, Calendar.getInstance().getTime.getHours)
        val pubMsg = deviceData.toString()
        //val pubMsg = "{\"pubmsg\":" + i + "}"
        val pubQoS = 0
        val message = new MqttMessage(pubMsg.getBytes)
        message.setQos(pubQoS)
        message.setRetained(false)
        // Publish the message
        System.out.println("Publishing to topic \"" + mqttTopic.getName + "\" qos " + pubQoS)
        var token = null
        try { // publish message to broker
          val token: MqttDeliveryToken = mqttTopic.publish(message)
          // Wait until the message has been delivered to the broker
          token.waitForCompletion()
          Thread.sleep(2000)
        } catch {
          case e: Exception =>
            e.printStackTrace()
        }

        {
          i += 1; i - 1
        }
      }
    }
  }

  def setSubscribe(client: MqttClient, topic: String): Unit = {
    if (isSubscriber) try {
      val subQoS = 0
      client.subscribe(topic, subQoS)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

}
