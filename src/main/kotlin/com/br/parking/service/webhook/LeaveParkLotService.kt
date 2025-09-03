package com.br.parking.service.webhook

import com.br.parking.api.request.ParkLotRequest
import com.br.parking.infrastructure.repository.parklot.ParkLotEntity
import com.br.parking.service.ParkLotService
import com.br.parking.service.SpotService
import com.br.parking.service.calculator.PriceCalculatorInput
import com.br.parking.service.calculator.PriceCalculatorService
import com.br.parking.shared.enums.ParkLotStatusEnum
import com.br.parking.shared.error.ParkLotNotFountWithActiveStatusByLicensePlateException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.Duration
import java.time.LocalDateTime

@Service
class LeaveParkLotService(
  private val parkLotService: ParkLotService,
  private val spotService: SpotService,
  private val priceCalculatorService: PriceCalculatorService
) : WebhookService{
  private val log = LoggerFactory.getLogger(LeaveParkLotService::class.java)

  override fun process(request: ParkLotRequest) {
    val parkLot = findByPlateAndStatusParked(request.licensePlate)

    parkLot.status = ParkLotStatusEnum.EXITED
    parkLot.exitTime = request.exitTime
    parkLot.totalMinutes = calculateParkingTime(parkLot.parkedTime!!, request.exitTime!!)
    val calculatePrice = build(parkLot)

    parkLot.finalPrice = priceCalculatorService.calculatePrice(calculatePrice)
    log.info("Veículo saíndo do estacionamento, placa: {}, tempo estacionado: {} minutos, preço final: {}", request.licensePlate, parkLot.totalMinutes, parkLot.finalPrice)

    spotService.updateSpotToFree(parkLot.spotId)
    log.info("Liberando vaga do id: {}", parkLot.spotId)

    parkLotService.save(parkLot)
    log.info("Saida registrada com sucesso!")
  }

  fun calculateParkingTime(entryTime: LocalDateTime, exitTime: LocalDateTime): Int {
    return Duration.between(entryTime, exitTime).toMinutes().toInt()
  }

  private fun build(parkLot: ParkLotEntity): PriceCalculatorInput{
    return PriceCalculatorInput(
      entryTime = parkLot.parkedTime!!,
      exitTime = parkLot.exitTime!!,
      basePrice = parkLot.basePrice!!,
      durationLimitMinutes = parkLot.durationLimitMinutes,
      charge = parkLot.amountCharge ?: BigDecimal.ZERO
    )
  }

  private fun findByPlateAndStatusParked(plate: String): ParkLotEntity {
    return parkLotService.findByPlateAndStatus(plate, ParkLotStatusEnum.PARKED)
      ?: throw ParkLotNotFountWithActiveStatusByLicensePlateException()
  }

}