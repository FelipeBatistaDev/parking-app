package com.br.parking.api.request

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class RevenueRequest(
  @JsonProperty(value = "sector")
  val sector: String,

  @JsonProperty(value = "date")
  val date: LocalDate
)
