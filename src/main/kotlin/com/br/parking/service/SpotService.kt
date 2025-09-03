package com.br.parking.service

import com.br.parking.infrastructure.repository.spot.SpotEntity
import com.br.parking.infrastructure.repository.spot.SpotRepository
import com.br.parking.shared.error.SpotGenericErrorException
import com.br.parking.shared.error.SpotStatusNotFoundErrorException
import com.br.parking.shared.error.SpotsNotAvailableException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SpotService(
    private val spotRepository: SpotRepository,
){

  fun findNextAvailableSpot(): SpotEntity? {
    return try {
      spotRepository.findFirstByOccupied(false)
    } catch (exception: Exception) {
      throw SpotGenericErrorException(exception)
    }
  }

  fun saveSpot(spot: SpotEntity) {
    try {
      spotRepository.save(spot)
    } catch (e: Exception) {
      throw SpotGenericErrorException(e)
    }
  }

  fun countPercentOccupiedSpots(): Int {
    return try {
      spotRepository.countByOccupied(true) * 100 / spotRepository.count().toInt()
    } catch (exception: Exception) {
      throw SpotGenericErrorException(exception)
    }
  }

  fun updateSpotToOccupied(spot: SpotEntity) {
    try {
      spot.occupied = true
      spotRepository.save(spot)
    } catch (exception: Exception) {
      throw SpotGenericErrorException(exception)
    }
  }

  fun updateSpotToFree(spotId: Int) {
    try {
      val spot = spotRepository.getReferenceById(spotId)
      spot.occupied = false
      spotRepository.save(spot)
    } catch (exception: Exception) {
      throw SpotGenericErrorException(exception)
    }
  }
}