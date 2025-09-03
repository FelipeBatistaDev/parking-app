package com.br.parking.service

import com.br.parking.infrastructure.repository.sector.SectorEntity
import com.br.parking.infrastructure.repository.sector.SectorRepository
import com.br.parking.shared.error.SectorGenericErrorException
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito.*
import java.math.BigDecimal
import java.time.LocalTime
import java.util.*
import kotlin.test.Test

class SectorServiceTest {

  private lateinit var sectorRepository: SectorRepository
  private lateinit var sectorService: SectorService

  @BeforeEach
  fun setUp() {
    sectorRepository = mock(SectorRepository::class.java)
    sectorService = SectorService(sectorRepository)
  }

  @Test
  @DisplayName("Salva setor com sucesso")
  fun process_Save_Sector_Success() {
    val sector = SectorEntity(
      sector = "A",
      basePrice = BigDecimal("10.00"),
      maxCapacityInteger = 50,
      openHour = LocalTime.of(8, 0),
      closeHour = LocalTime.of(18, 0),
      durationLimitMinutes = 120
    )

    sectorService.saveSector(sector)

    verify(sectorRepository).save(sector)
  }

  @Test
  @DisplayName("Lança exceção setor ao tentar salvar")
  fun process_Save_Sector_Throws_Exception() {
    val sector = SectorEntity(
      sector = "B",
      basePrice = BigDecimal("15.00"),
      maxCapacityInteger = 30,
      openHour = LocalTime.of(9, 0),
      closeHour = LocalTime.of(17, 0),
      durationLimitMinutes = 90
    )
    `when`(sectorRepository.save(sector)).thenThrow(RuntimeException("Erro ao salvar setor"))

    assertThrows<SectorGenericErrorException> {
      sectorService.saveSector(sector)
    }
  }

  @Test
  @DisplayName("Retorna setor buscado com sucesso")
  fun process_Find_Sector_Success() {
    val sector = SectorEntity(
      sector = "X",
      basePrice = BigDecimal("25.00"),
      maxCapacityInteger = 60,
      openHour = LocalTime.of(6, 0),
      closeHour = LocalTime.of(22, 0),
      durationLimitMinutes = 180
    )
    `when`(sectorRepository.findById("X")).thenReturn(Optional.of(sector))

    val result = sectorService.findSectorById("X")

    assertNotNull(result)
    assertEquals("X", result.sector)
    assertEquals(BigDecimal("25.00"), result.basePrice)
    assertEquals(60, result.maxCapacityInteger)
    assertEquals(LocalTime.of(6, 0), result.openHour)
    assertEquals(LocalTime.of(22, 0), result.closeHour)
    assertEquals(180, result.durationLimitMinutes)
  }

  @Test
  @DisplayName("Retorna nulo quando não achar setor buscado")
  fun process_Find_Sector_NUll() {
    `when`(sectorRepository.findById("Y")).thenReturn(Optional.empty())

    val result = sectorService.findSectorById("Y")

    assertNull(result)
  }

  @Test
  @DisplayName("Lança excecao quando ocorre erro no repositório")
  fun process_Find_Sector_Throws_Exception() {
    `when`(sectorRepository.findById("Z")).thenThrow(RuntimeException("Erro ao buscar setor"))

    assertThrows<SectorGenericErrorException> {
      sectorService.findSectorById("Z")
    }
  }


}