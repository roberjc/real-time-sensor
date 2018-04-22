package model

import java.util.Calendar

case class DeviceData(deviceId: Int, eventTime: Calendar, measures: List[(String, Double)])
