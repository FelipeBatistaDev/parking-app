package com.br.parking.infrastructure.repository.licensePlateStatus

import java.math.BigDecimal
import java.time.LocalDateTime

data class LicensePlateStatusOutput(
  val licensePlate: String,
  val parklotStatus: String,
  val lat: Double,
  val lng: Double,
  val entryTime: LocalDateTime?,
  val timeParked: LocalDateTime?,
  val charge: BigDecimal?,
  val basePrice: BigDecimal,
  val duration: Int
)

