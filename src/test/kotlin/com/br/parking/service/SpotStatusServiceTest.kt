package com.br.parking.service

import com.br.parking.api.request.SpotStatusRequest
import com.br.parking.infrastructure.repository.spot.SpotEntity
import com.br.parking.infrastructure.repository.spot.SpotRepository
import com.br.parking.shared.error.SpotStatusNotFoundErrorException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import kotlin.test.Test

class SpotStatusServiceTest{
  private lateinit var repository: SpotRepository
  private lateinit var service: SpotStatusService

  @BeforeEach
  fun setUp() {
    repository = mock(SpotRepository::class.java)
    service = SpotStatusService(repository)
  }

  @Test
  @DisplayName("Testa busca de vaga por Lat e Lng sucesso")
  fun process_FindBy_LatLng_Success() {
    val request = SpotStatusRequest(lat = "10.000", lng = "20.000")
    val spot = SpotEntity(
      id = 1,
      sector = "A",
      lat = "10.000",
      lng = "20.000",
      occupied = true
    )
    `when`(repository.findByLatAndLng("10.000", "20.000")).thenReturn(spot)

    val response = service.findByLatLng(request)

    assertEquals(1, response.id)
    assertEquals("A", response.sector)
    assertEquals("10.000", response.lat)
    assertEquals("20.000", response.lng)
    assertEquals(true, response.occupied)
  }

  @Test
  @DisplayName("Testa busca de vaga por Lat e Lng falha")
  fun process_FindBy_LatLng_Failed() {
    val request = SpotStatusRequest(lat = "10.000", lng = "20.000")
    `when`(repository.findByLatAndLng("10.000", "20.000")).thenReturn(null)

    assertThrows<SpotStatusNotFoundErrorException> {
      service.findByLatLng(request)
    }
  }

}