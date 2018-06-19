package mqtt

import org.eclipse.paho.client.mqttv3.{MqttClient, MqttConnectOptions, MqttException}


object MqttConfig extends App {

    val brokerUrl = "tcp://master:1883"
    val domain = "testtopic"
    val subdomain = ""
    val deviceId = "MyLaptop"
    val user = "robe"
    val pass = "robe"

    val mqtt = MqttProducer

    val mqttOpts: MqttConnectOptions = mqtt.connectOptions

    mqttOpts.setCleanSession(true)
    mqttOpts.setKeepAliveInterval(30)
    mqttOpts.setUserName(user)
    mqttOpts.setPassword(pass.toCharArray)

    try {
      val client = new MqttClient(brokerUrl, deviceId)
      client.setCallback(mqtt)
      client.connect(mqttOpts)
      System.out.println(">> Connected to Mosquitto broker: " + brokerUrl + "\n")
      val topic = mqtt.setTopic(client, domain)
      mqtt.setPublish(topic)

    } catch {
      case e: MqttException =>
        e.printStackTrace()
        System.exit(-1)
    }
}
