package producers

import org.eclipse.paho.client.mqttv3._

object MqttClient extends MqttCallback{

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
        i <= 10
      }) {
        val pubMsg = "{\"pubmsg\":" + i + "}"
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
          Thread.sleep(100)
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
