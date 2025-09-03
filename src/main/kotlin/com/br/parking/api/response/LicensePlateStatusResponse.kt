package com.br.parking.api.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.LocalDateTime

data class LicensePlateStatusResponse(
  @JsonProperty(value = "license_plate")
  val licensePlate: String?,

  @JsonProperty(value = "price_until_now")
  val priceUntilNow: BigDecimal?,

  @JsonProperty(value = "entry_time")
  val entryTime: LocalDateTime?,

  @JsonProperty(value = "time_parked")
  val timeParked: LocalDateTime?,

  @JsonProperty(value = "lat")
  val lat: String,

  @JsonProperty(value = "lng")
  val lng: String
)
