package com.br.parking.service

import com.br.parking.api.request.RevenueRequest
import com.br.parking.infrastructure.repository.revenue.RevenueOutput
import com.br.parking.infrastructure.repository.revenue.RevenueRepository
import com.br.parking.shared.error.RevenueNotFoundErrorException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.test.Test

class RevenueServiceTest{
  private lateinit var revenueRepository: RevenueRepository
  private lateinit var service: RevenueService

  @BeforeEach
  fun setUp() {
    revenueRepository = mock(RevenueRepository::class.java)
    service = RevenueService(revenueRepository)
  }

  @Test
  @DisplayName("Retorna soma das receitas com sucesso")
  fun process_Revenue_Success() {
    val request = RevenueRequest(
      sector = "A1",
      date = LocalDate.of(2024, 6, 1)
    )
    val revenues = listOf(
      RevenueOutput(finalPrice = BigDecimal("150.00"), charge = BigDecimal("50.00"), entryTime = LocalDate.of(2024, 6, 1).atStartOfDay(), exitTime = null),
      RevenueOutput(finalPrice = BigDecimal("250.00"), charge = BigDecimal("100.00"), entryTime = LocalDate.of(2024, 6, 1).atStartOfDay(), exitTime = null)
    )
    `when`(revenueRepository.findByDateAndSector("A1", LocalDate.of(2024, 6, 1))).thenReturn(revenues)

    val response = service.findByDateAndSector(request)

    assertEquals(BigDecimal("400.00"), response.amount)
    assertEquals(LocalDate.of(2024, 6, 1), response.timestamp)
  }

  @Test
  @DisplayName("Retorna 0 quando não há receitas ainda")
  fun process_Revenue_Empty_List() {
    val request = RevenueRequest(
      sector = "B",
      date = LocalDate.of(2024, 6, 1)
    )
    `when`(revenueRepository.findByDateAndSector("B", LocalDate.of(2024, 6, 1))).thenReturn(emptyList())

    assertThrows<RevenueNotFoundErrorException> {
      service.findByDateAndSector(request)
    }
  }
}