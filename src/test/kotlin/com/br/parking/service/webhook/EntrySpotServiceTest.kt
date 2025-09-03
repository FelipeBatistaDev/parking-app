package com.br.parking.service.webhook

import com.br.parking.api.request.ParkLotRequest
import com.br.parking.infrastructure.repository.parklot.ParkLotEntity
import com.br.parking.infrastructure.repository.sector.SectorEntity
import com.br.parking.infrastructure.repository.spot.SpotEntity
import com.br.parking.service.ParkLotService
import com.br.parking.service.SectorService
import com.br.parking.service.SpotService
import com.br.parking.shared.enums.EventTypeEnum
import com.br.parking.shared.enums.ParkLotStatusEnum
import com.br.parking.shared.error.SectorNotFoundErrorException
import com.br.parking.shared.error.SpotsNotAvailableException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.test.Test

class EntrySpotServiceTest{
  private lateinit var parkLotService: ParkLotService
  private lateinit var spotService: SpotService
  private lateinit var sectorService: SectorService
  private lateinit var service: EntrySpotService

  @BeforeEach
  fun setUp() {
    parkLotService = mock(ParkLotService::class.java)
    spotService = mock(SpotService::class.java)
    sectorService = mock(SectorService::class.java)
    service = EntrySpotService(parkLotService, spotService, sectorService)
  }

  @Test
  @DisplayName("Salva novo status da vaga para estacionado")
  fun process_SavesParkLotAndMarksSpotOccupied_WhenAllDependenciesReturnValidData() {
    val request = ParkLotRequest(
      licensePlate = "ABC1234",
      entryTime = LocalDateTime.now(),
      exitTime = null,
      latitude = "10.000",
      longitude = "20.000",
      eventType = EventTypeEnum.PARKED
    )
    val spot = SpotEntity(id = 1, sector = "sector1")
    val sector = SectorEntity(sector = "sector1", basePrice = BigDecimal(50), durationLimitMinutes = 120)
    val parkLot = ParkLotEntity(licensePlate = "ABC1234", status = ParkLotStatusEnum.ACTIVE)

    `when`(spotService.findNextAvailableSpot()).thenReturn(spot)
    `when`(sectorService.findSectorById("sector1")).thenReturn(sector)
    `when`(parkLotService.findByPlateAndStatus("ABC1234", ParkLotStatusEnum.ACTIVE)).thenReturn(parkLot)

    service.process(request)

    verify(parkLotService).save(parkLot.apply {
      spotId = 1
      basePrice = BigDecimal(50)
      status = ParkLotStatusEnum.PARKED
      durationLimitMinutes = 120
    })
    verify(spotService).updateSpotToOccupied(spot)
  }

  @Test
  @DisplayName("Valida quando há vagas")
  fun findNextAvailableSpot_ReturnsSpot_WhenSpotIsAvailable() {
    val spot = SpotEntity(id = 1, sector = "sector1", occupied = false)

    `when`(spotService.findNextAvailableSpot()).thenReturn(spot)

    val result = spotService.findNextAvailableSpot()

    assertEquals(spot, result)
    verify(spotService).findNextAvailableSpot()
  }

  @Test
  @DisplayName("Valida quando não acha vagas disponiveis se lança exceçao")
  fun process_ThrowsException_WhenNoAvailableSpotIsFound() {
    val request = ParkLotRequest(
      licensePlate = "DEF5678",
      entryTime = LocalDateTime.now(),
      exitTime = null,
      latitude = "15.000",
      longitude = "25.000",
      eventType = EventTypeEnum.PARKED
    )

    `when`(spotService.findNextAvailableSpot()).thenReturn(null)

    assertThrows<SpotsNotAvailableException> {
      service.process(request)
    }

    verify(spotService).findNextAvailableSpot()
  }

}