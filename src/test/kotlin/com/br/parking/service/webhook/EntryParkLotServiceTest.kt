package com.br.parking.service.webhook

import com.br.parking.api.request.ParkLotRequest
import com.br.parking.infrastructure.repository.parklot.ParkLotEntity
import com.br.parking.service.ParkLotService
import com.br.parking.service.SpotService
import com.br.parking.service.chargefactory.TestCapacityResultOutput
import com.br.parking.shared.enums.EventTypeEnum
import com.br.parking.shared.error.FullGarageException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.mockito.kotlin.argumentCaptor
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.test.Test

class EntryParkLotServiceTest {

  private lateinit var parkLotService: ParkLotService
  private lateinit var spotService: SpotService
  private lateinit var service: EntryParkLotService

  @BeforeEach
  fun setUp() {
    parkLotService = mock(ParkLotService::class.java)
    spotService = mock(SpotService::class.java)
    service = EntryParkLotService(parkLotService, spotService)
  }

  @DisplayName("Deve processar entrada com porcentagem ocupada entre 25% e 50% com sucesso sem usar ParkLotMapper no teste")
  @Test
  fun processEntryWithOccupiedPercentageBetween25And50SuccessfullyWithoutParkLotMapper() {
    // Arrange
    val request = ParkLotRequest(
      licensePlate = "DEF4567",
      entryTime = LocalDateTime.now(),
      exitTime = null,
      latitude = "12.3456",
      longitude = "65.4321",
      eventType = EventTypeEnum.ENTRY
    )
    val percentageOccupied = 20
    val testCapacityResult = TestCapacityResultOutput(charge = BigDecimal("-10"))

    `when`(spotService.countPercentOccupiedSpots()).thenReturn(percentageOccupied)

    service.process(request)

    verify(spotService).countPercentOccupiedSpots()

    val captor = argumentCaptor<ParkLotEntity>()
    verify(parkLotService).save(captor.capture())
    val savedEntity = captor.firstValue

    assertEquals(request.licensePlate, savedEntity.licensePlate)
    assertEquals(request.entryTime, savedEntity.parkedTime)
    assertEquals(request.eventType.status, savedEntity.status)
    assertEquals(testCapacityResult.charge, savedEntity.amountCharge)
  }

  @DisplayName("Deve barrar entrada se porcentagem for 100")
  @Test
  fun processEntryWithOccupiedPercentage100ThrowsException() {
    // Arrange
    val request = ParkLotRequest(
      licensePlate = "DEF4567",
      entryTime = LocalDateTime.now(),
      exitTime = null,
      latitude = "12.3456",
      longitude = "65.4321",
      eventType = EventTypeEnum.ENTRY
    )

    `when`(spotService.countPercentOccupiedSpots()).thenReturn(100)

    assertThrows<FullGarageException> {
      service.process(request)
    }
  }



}