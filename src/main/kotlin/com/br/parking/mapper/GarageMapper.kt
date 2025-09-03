package com.br.parking.mapper

import com.br.parking.infrastructure.client.SectorDTO
import com.br.parking.infrastructure.client.SpotDTO
import com.br.parking.infrastructure.repository.sector.SectorEntity
import com.br.parking.infrastructure.repository.spot.SpotEntity
import java.time.LocalTime

object GarageMapper {

  fun toSector(dto: SectorDTO): SectorEntity{
    return SectorEntity(
      sector = dto.sector,
      basePrice = dto.base_price,
      maxCapacityInteger = dto.max_capacity,
      openHour = LocalTime.parse(dto.open_hour),
      closeHour = LocalTime.parse(dto.close_hour),
      durationLimitMinutes = dto.duration_limit_minutes
    )
  }

  fun toSpot(dto: SpotDTO): SpotEntity{
    return SpotEntity(
      id = dto.id,
      sector = dto.sector,
      lat = dto.lat,
      lng = dto.lng,
      occupied = dto.occupied
    )
  }
}