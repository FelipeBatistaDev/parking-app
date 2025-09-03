package com.br.parking.mapper

import com.br.parking.api.request.ParkLotRequest
import com.br.parking.infrastructure.repository.parklot.ParkLotEntity

object ParkLotMapper {

  fun toEntity(request: ParkLotRequest): ParkLotEntity{
    return ParkLotEntity(
      licensePlate = request.licensePlate,
      parkedTime = request.entryTime,
      status = request.eventType.status
    )
  }
}