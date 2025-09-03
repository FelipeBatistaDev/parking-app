package com.br.parking.api.request

import com.br.parking.shared.enums.EventTypeEnum
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class ParkLotRequest(
  @JsonProperty("license_plate")
  val licensePlate: String,

  @JsonProperty("entry_time")
  val entryTime: LocalDateTime?,

  @JsonProperty("exit_time")
  val exitTime: LocalDateTime?,

  @JsonProperty("lat")
  val latitude: String?,

  @JsonProperty("lng")
  val longitude: String?,

  @JsonProperty("event_type")
  val eventType: EventTypeEnum
)
