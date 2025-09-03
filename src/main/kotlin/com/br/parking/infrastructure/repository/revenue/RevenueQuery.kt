package com.br.parking.infrastructure.repository.revenue

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
@PropertySource("classpath:/queries/revenue.properties")

class RevenueQuery(
  @Value("\${revenue.select}") private val selectQuery: String
) {
  fun select() = selectQuery
}

