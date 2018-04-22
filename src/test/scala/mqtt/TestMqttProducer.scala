package mqtt

import producers.MqttClient
import org.eclipse.paho.client.mqttv3.{MqttClient, MqttConnectOptions, MqttException, MqttTopic}

object TestMqttProducer {

  def main(args: Array[String]): Unit = {

    val brokerUrl = "ws://localhost:9001"
    val domain = "testtopic"
    val subdomain = ""
    val deviceId = "clientId-serobe"
    val user = "robe"
    val pass = "mosquitto"

    val mqtt = MqttClient

    val mqttOpts: MqttConnectOptions = mqtt.connectOptions

    mqttOpts.setCleanSession(true)
    mqttOpts.setKeepAliveInterval(30)
    mqttOpts.setUserName(user)
    mqttOpts.setPassword(pass.toCharArray)

    try {
      val client = new MqttClient(brokerUrl, deviceId)
      client.setCallback(mqtt)
      client.connect(mqttOpts)
      System.out.println("Conectado a: " + brokerUrl)
      val topic = mqtt.setTopic(client, domain)
      mqtt.setPublish(topic)
      //mqtt.setSubscribe(client, domain);
    } catch {
      case e: MqttException =>
        e.printStackTrace()
        System.exit(-1)
    }
  }
}
