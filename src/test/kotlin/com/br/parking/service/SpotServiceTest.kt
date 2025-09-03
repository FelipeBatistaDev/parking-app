package com.br.parking.service

import com.br.parking.infrastructure.repository.spot.SpotEntity
import com.br.parking.infrastructure.repository.spot.SpotRepository
import com.br.parking.shared.error.SpotGenericErrorException
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import kotlin.test.Test

class SpotServiceTest{
  private lateinit var spotRepository: SpotRepository
  private lateinit var spotService: SpotService

  @BeforeEach
  fun setUp() {
    spotRepository = mock(SpotRepository::class.java)
    spotService = SpotService(spotRepository)
  }

  @Test
  @DisplayName("Procura vaga disponível com sucesso")
  fun process_Find_Available_Spot_Success() {
    val spot = SpotEntity(
      id = 1,
      sector = "A",
      lat = "10.000",
      lng = "20.000",
      occupied = false
    )
    `when`(spotRepository.findFirstByOccupied(false)).thenReturn(spot)

    val result = spotService.findNextAvailableSpot()

    assertNotNull(result)
    assertEquals(1, result.id)
    assertEquals("A", result.sector)
    assertEquals("10.000", result.lat)
    assertEquals("20.000", result.lng)
    assertEquals(false, result.occupied)
  }

  @Test
  @DisplayName("Retorno de quando não a vaga disponível")
  fun process_Find_Available_Spot_Return_Null() {
    `when`(spotRepository.findFirstByOccupied(false)).thenReturn(null)

    val result = spotService.findNextAvailableSpot()

    assertNull(result)
  }

  @Test
  @DisplayName("Falha ao buscar vaga disponível")
  fun process_Find_Available_Spot_Throws_Exception() {
    `when`(spotRepository.findFirstByOccupied(false)).thenThrow(RuntimeException("Erro no repositório"))

    assertThrows<SpotGenericErrorException> {
      spotService.findNextAvailableSpot()
    }
  }

  @Test
  @DisplayName("Salva vaga com sucesso")
  fun process_Save_Spot_Returns_Success() {
    val spot = SpotEntity(
      id = 1,
      sector = "A",
      lat = "10.000",
      lng = "20.000",
      occupied = false
    )
    `when`(spotRepository.save(spot)).thenReturn(spot)

    spotService.saveSpot(spot)

    assertNotNull(spot)
    assertEquals(1, spot.id)
    assertEquals("A", spot.sector)
    assertEquals("10.000", spot.lat)
    assertEquals("20.000", spot.lng)
    assertEquals(false, spot.occupied)
  }

  @Test
  @DisplayName("Exceçao ao salvar vaga")
  fun process_Save_Spot_Throws_Exception() {
    val spot = SpotEntity(
      id = 1,
      sector = "A",
      lat = "10.000",
      lng = "20.000",
      occupied = false
    )
    `when`(spotRepository.save(spot)).thenThrow(RuntimeException("Erro ao salvar vaga"))

    assertThrows<SpotGenericErrorException> {
      spotService.saveSpot(spot)
    }
  }

  @Test
  @DisplayName("Calcula porcentagem de vagas ocupadas")
  fun process_CountOccupiedPercentage_Success() {
    `when`(spotRepository.countByOccupied(true)).thenReturn(5)
    `when`(spotRepository.count()).thenReturn(10L)

    val result = spotService.countPercentOccupiedSpots()

    assertEquals(50, result)
  }

  @Test
  @DisplayName("retorna 0 quando não há vagas ocupadas")
  fun process_CountOccupiedPercentage_WhenIs_Zero() {
    `when`(spotRepository.countByOccupied(true)).thenReturn(0)
    `when`(spotRepository.count()).thenReturn(10L)

    val result = spotService.countPercentOccupiedSpots()

    assertEquals(0, result)
  }

  @Test
  @DisplayName("Atualiza status da vaga com sucesso")
  fun process_UpdateSpot_Status_Success() {
    val spot = SpotEntity(
      id = 1,
      sector = "A",
      lat = "10.000",
      lng = "20.000",
      occupied = false
    )
    `when`(spotRepository.save(spot)).thenReturn(spot)

    spotService.updateSpotToOccupied(spot)

    assertEquals(true, spot.occupied)
  }

  @Test
  @DisplayName("Erro ao salvar status da vaga")
  fun process_UpdateSpot_Status_Error() {
    val spot = SpotEntity(
      id = 1,
      sector = "A",
      lat = "10.000",
      lng = "20.000",
      occupied = false
    )
    `when`(spotRepository.save(spot)).thenThrow(RuntimeException("Erro ao salvar vaga"))

    assertThrows<SpotGenericErrorException> {
      spotService.updateSpotToOccupied(spot)
    }
  }

  @Test
  @DisplayName("Atualiza status da vaga para livre com sucesso")
  fun process_UpdateSpot_Status_Free_Success() {
    val spot = SpotEntity(
      id = 1,
      sector = "A",
      lat = "10.000",
      lng = "20.000",
      occupied = true
    )
    `when`(spotRepository.getReferenceById(1)).thenReturn(spot)
    `when`(spotRepository.save(spot)).thenReturn(spot)

    spotService.updateSpotToFree(1)

    assertEquals(false, spot.occupied)
  }

  @Test
  @DisplayName("Atualiza status da vaga para livre com falha")
  fun process_UpdateSpot_Status_Free_Failed() {
    `when`(spotRepository.getReferenceById(1)).thenThrow(RuntimeException("Erro ao buscar vaga"))

    assertThrows<SpotGenericErrorException> {
      spotService.updateSpotToFree(1)
    }
  }
}