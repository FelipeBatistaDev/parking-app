package com.br.parking.service

import com.br.parking.api.request.SpotStatusRequest
import com.br.parking.api.response.SpotStatusResponse
import com.br.parking.infrastructure.repository.spot.SpotRepository
import com.br.parking.mapper.SpotStatusMapper
import com.br.parking.shared.error.SpotStatusNotFoundErrorException
import org.springframework.stereotype.Service

@Service
class SpotStatusService(
  private val repository: SpotRepository,
) {

  fun findByLatLng(dto: SpotStatusRequest): SpotStatusResponse {
    val spotStatus = repository.findByLatAndLng(dto.lat, dto.lng) ?: throw SpotStatusNotFoundErrorException()

    return SpotStatusMapper.toResponse(spotStatus)
  }

}