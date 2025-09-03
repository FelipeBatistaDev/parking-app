package com.br.parking.service.webhook

import com.br.parking.api.request.ParkLotRequest
import com.br.parking.mapper.ParkLotMapper
import com.br.parking.service.ParkLotService
import com.br.parking.service.SpotService
import com.br.parking.service.chargefactory.TestCapacityResultFactory
import com.br.parking.service.chargefactory.TestCapacityResultOutput
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class EntryParkLotService(
  private val parkLotService: ParkLotService,
  private val spotService: SpotService,
) : WebhookService{
  private val log = LoggerFactory.getLogger(EntryParkLotService::class.java)

  override fun process(request: ParkLotRequest) {
    log.info("Nova entrada na garagem, placa: {}", request.licensePlate)

    val parkLot = ParkLotMapper.toEntity(request)
    val percentageOccupied = spotService.countPercentOccupiedSpots()
    val testCapacityResult = chargeSelector(percentageOccupied)

    log.info("Porcentagem da garagem ocupada: {}, porcentagem aplicada: {}", percentageOccupied, testCapacityResult.charge )

    parkLot.amountCharge = testCapacityResult.charge


    parkLotService.save(parkLot)
    log.info("Nova entrada salva com sucesso: {}", request.licensePlate)
  }

  private fun chargeSelector(percentageOccupied: Int): TestCapacityResultOutput {
    return when {
      percentageOccupied <= 25 -> TestCapacityResultFactory.lowerThan25Percent()
      percentageOccupied <= 50 -> TestCapacityResultFactory.lowerThan50Percent()
      percentageOccupied <= 75 -> TestCapacityResultFactory.lowerThan75Percent()
      else -> TestCapacityResultFactory.lowerThan100Percent()
    }
  }
}