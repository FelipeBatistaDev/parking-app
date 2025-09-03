package com.br.parking.api.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.LocalDate

data class RevenueResponse(
  @JsonProperty(value = "currency")
  val  currency: String,

  @JsonProperty(value = "amount")
  val  amount: BigDecimal,

  @JsonProperty(value = "timestamp")
  val  timestamp: LocalDate
)
