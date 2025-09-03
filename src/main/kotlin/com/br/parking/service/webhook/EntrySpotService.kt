package com.br.parking.service.webhook

import com.br.parking.api.request.ParkLotRequest
import com.br.parking.infrastructure.repository.parklot.ParkLotEntity
import com.br.parking.infrastructure.repository.sector.SectorEntity
import com.br.parking.infrastructure.repository.spot.SpotEntity
import com.br.parking.service.ParkLotService
import com.br.parking.service.SectorService
import com.br.parking.service.SpotService
import com.br.parking.shared.enums.ParkLotStatusEnum
import com.br.parking.shared.error.ParkLotNotFountWithActiveStatusByLicensePlateException
import com.br.parking.shared.error.SectorNotFoundErrorException
import com.br.parking.shared.error.SpotsNotAvailableException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class EntrySpotService(
  private val parkLotService: ParkLotService,
  private val spotService: SpotService,
  private val sectorService: SectorService,
): WebhookService  {
  private val log = LoggerFactory.getLogger(EntrySpotService::class.java)

  override fun process(request: ParkLotRequest) {
    val spot = findNextAvailableSpot()
    val sector = findSectorById(spot.sector)
    val parkLot = findByPlateAndStatusActive(request.licensePlate)


    parkLot.spotId = spot.id
    parkLot.basePrice = sector.basePrice
    parkLot.status = ParkLotStatusEnum.PARKED
    parkLot.durationLimitMinutes = sector.durationLimitMinutes

    log.info("Carro com a placa: {}, está estacionando na vaga: {}, no setor: {}", request.licensePlate, spot.id, spot.sector)

    parkLotService.save(parkLot)
    log.info("Salvo com sucesso dados do veículo com a placa: {}", request.licensePlate)

    spotService.updateSpotToOccupied(spot)
    log.info("Spot marcado como ocupado: {}", spot.id)
  }

  private fun findNextAvailableSpot(): SpotEntity {
    return spotService.findNextAvailableSpot() ?: throw SpotsNotAvailableException()
  }

  private fun findSectorById(id: String): SectorEntity {
    return sectorService.findSectorById(id) ?: throw SectorNotFoundErrorException()
  }

  private fun findByPlateAndStatusActive(plate: String): ParkLotEntity {
    return parkLotService.findByPlateAndStatus(plate, ParkLotStatusEnum.ACTIVE) ?: throw ParkLotNotFountWithActiveStatusByLicensePlateException()
  }

}