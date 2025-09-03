package com.br.parking.infrastructure.client

import java.math.BigDecimal

data class SectorDTO(
  val sector: String,
  val base_price: BigDecimal,
  val max_capacity: Int,
  val open_hour: String,
  val close_hour: String,
  val duration_limit_minutes: Int
)
