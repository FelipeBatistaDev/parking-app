package com.br.parking.mapper

import com.br.parking.api.response.SpotStatusResponse
import com.br.parking.infrastructure.repository.spot.SpotEntity

object SpotStatusMapper {
  fun toResponse(spotStatusOutput: SpotEntity): SpotStatusResponse{
    return SpotStatusResponse(
      id = spotStatusOutput.id,
      sector = spotStatusOutput.sector,
      lat = spotStatusOutput.lat,
      lng = spotStatusOutput.lng,
      occupied = spotStatusOutput.occupied
    )
  }
}
