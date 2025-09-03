package com.br.parking.service.calculator

import java.math.BigDecimal
import java.time.LocalDateTime

data class PriceCalculatorInput(
  val entryTime: LocalDateTime,
  val exitTime: LocalDateTime,
  val basePrice: BigDecimal,
  val charge: BigDecimal,
  val durationLimitMinutes: Int,
)
