package com.br.parking.service

import com.br.parking.api.request.RevenueRequest
import com.br.parking.api.response.RevenueResponse
import com.br.parking.infrastructure.repository.revenue.RevenueOutput
import com.br.parking.infrastructure.repository.revenue.RevenueRepository
import com.br.parking.mapper.RevenueMapper
import com.br.parking.shared.error.RevenueNotFoundErrorException
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class RevenueService(
  private val revenueRepository: RevenueRepository
) {
  fun findByDateAndSector(request: RevenueRequest): RevenueResponse {
    val revenues = findRevenues(request)
    val finalPrice = revenues.map { it.finalPrice }.fold(BigDecimal.ZERO) { totalAmount, value -> totalAmount.add(value) }

    return RevenueMapper.toResponse(finalPrice, request.date)
  }

  private fun findRevenues(dto: RevenueRequest): List<RevenueOutput> {
    val date = dto.date
    val codSector = dto.sector
    val revenues = revenueRepository.findByDateAndSector(codSector, date)
    if (revenues.isEmpty()) {
      throw RevenueNotFoundErrorException()
    }

    return revenues
  }

}