package com.br.parking.service

import com.br.parking.infrastructure.repository.parklot.ParkLotEntity
import com.br.parking.infrastructure.repository.parklot.ParkLotRepository
import com.br.parking.shared.enums.ParkLotStatusEnum
import com.br.parking.shared.error.ParkLotGenericErrorException
import org.springframework.stereotype.Service

@Service
class ParkLotService(
  private val parkLotRepository: ParkLotRepository,
  ) {

  fun save(parkLot: ParkLotEntity): ParkLotEntity {
    return try {
      parkLotRepository.save(parkLot)
    } catch (e: Exception) {
      throw ParkLotGenericErrorException(e)
    }
  }

  fun findByPlateAndStatus(plate: String, status: ParkLotStatusEnum): ParkLotEntity? {
    return try {
      parkLotRepository.findByLicensePlateAndStatus(plate, status)
    } catch (e: Exception) {
      throw ParkLotGenericErrorException(e)
    }
  }

}