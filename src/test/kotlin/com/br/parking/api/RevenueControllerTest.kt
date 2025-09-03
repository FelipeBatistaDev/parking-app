package com.br.parking.api

import com.br.parking.api.request.RevenueRequest
import com.br.parking.api.response.RevenueResponse
import com.br.parking.service.RevenueService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.test.Test

class RevenueControllerTest{
  private val revenueService: RevenueService = mock()
  private val controller = RevenueController(revenueService)

  @Test
  @DisplayName("Get revenue retornando sucesso")
  fun process_ReturnsRevenueResponse_WhenSectorAndDateAreValid() {
    val sector = "A"
    val date = LocalDate.of(2025, 1, 1)
    val expectedResponse = RevenueResponse(
      currency = "BRL",
      amount = BigDecimal("1000.00"),
      timestamp = date
    )

    `when`(revenueService.findByDateAndSector(RevenueRequest(sector, date))).thenReturn(expectedResponse)

    val response = controller.process(sector, date)

    assertEquals(expectedResponse.currency, response.currency)
    assertEquals(expectedResponse.amount, response.amount)
    assertEquals(expectedResponse.timestamp, response.timestamp)
  }

}