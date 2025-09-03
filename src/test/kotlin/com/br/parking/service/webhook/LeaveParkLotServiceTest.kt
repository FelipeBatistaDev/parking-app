package com.br.parking.service.webhook

import com.br.parking.api.request.ParkLotRequest
import com.br.parking.infrastructure.repository.parklot.ParkLotEntity
import com.br.parking.service.ParkLotService
import com.br.parking.service.SpotService
import com.br.parking.service.calculator.PriceCalculatorService
import com.br.parking.shared.enums.EventTypeEnum
import com.br.parking.shared.enums.ParkLotStatusEnum
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.test.Test

class LeaveParkLotServiceTest{
  private lateinit var parkLotService: ParkLotService
  private lateinit var spotService: SpotService
  private lateinit var priceCalculatorService: PriceCalculatorService
  private lateinit var service: LeaveParkLotService

  @BeforeEach
  fun setUp() {
    parkLotService = mock(ParkLotService::class.java)
    spotService = mock(SpotService::class.java)
    priceCalculatorService = mock(PriceCalculatorService::class.java)
    service = LeaveParkLotService(parkLotService, spotService, priceCalculatorService)
  }

  @Test
  @DisplayName("Saida com veículo, calculo do valor e liberação da vaga")
  fun process_UpdatesParkLotAndFreesSpot_WhenRequestIsValid() {
    val request = ParkLotRequest(
      licensePlate = "ABC1234",
      entryTime = LocalDateTime.now().minusHours(2),
      exitTime = LocalDateTime.now(),
      latitude = "10.000",
      longitude = "20.000",
      eventType = EventTypeEnum.EXIT,
    )
    val parkLot = ParkLotEntity(
      id = 1,
      licensePlate = "ABC1234",
      status = ParkLotStatusEnum.PARKED,
      parkedTime = request.entryTime,
      spotId = 1,
      basePrice = BigDecimal(10),
      durationLimitMinutes = 120,
      amountCharge = BigDecimal(0)
    )

    `when`(parkLotService.findByPlateAndStatus("ABC1234", ParkLotStatusEnum.PARKED)).thenReturn(parkLot)
    `when`(priceCalculatorService.calculatePrice(any())).thenReturn(BigDecimal(20))

    service.process(request)

    assertEquals(ParkLotStatusEnum.EXITED, parkLot.status)
    assertEquals(request.exitTime, parkLot.exitTime)
    assertEquals(120, parkLot.totalMinutes)
    assertEquals(BigDecimal(20), parkLot.finalPrice)
  }

}