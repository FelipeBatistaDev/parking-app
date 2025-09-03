package com.br.parking.infrastructure.repository.licensePlateStatus

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@PropertySource("classpath:/queries/license.plate.status.properties")
class LicensePlateStatusQuery(
  @Value("\${license.plate.status.select}")
  private val selectQuery: String
) {
  fun select() = selectQuery
}
