package com.br.parking.infrastructure.repository.revenue

import java.math.BigDecimal
import java.time.LocalDateTime

data class RevenueOutput(
        val charge: BigDecimal,
        val entryTime: LocalDateTime,
        val exitTime: LocalDateTime?,
        val finalPrice: BigDecimal
)
