package com.br.parking.mapper

import com.br.parking.api.response.RevenueResponse
import java.math.BigDecimal
import java.time.LocalDate

object RevenueMapper {

  fun toResponse(totalOfDay: BigDecimal, currentDate: LocalDate): RevenueResponse{
    return RevenueResponse(
      currency = "BRL",
      amount = totalOfDay,
      timestamp = currentDate
    )
  }
}
