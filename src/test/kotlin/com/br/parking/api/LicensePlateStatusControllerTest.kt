package com.br.parking.api

import com.br.parking.api.response.LicensePlateStatusResponse
import com.br.parking.service.LicensePlateStatusService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.test.Test

class LicensePlateStatusControllerTest{
  private val licensePlateStatusService: LicensePlateStatusService = mock()
  private val controller = LicensePlateStatusController(licensePlateStatusService)

  @Test
  @DisplayName("Get License Plate e retorna sucesso")
  fun processEvent_Returns200AndResponse_WhenLicensePlateIsValid() {
    val licensePlate = "ZUL0001"
    val expectedResponse = LicensePlateStatusResponse(
      licensePlate = licensePlate,
      priceUntilNow = BigDecimal("25.50"),
      entryTime = LocalDateTime.of(2023, 10, 1, 8, 0),
      timeParked = LocalDateTime.of(2023, 10, 1, 10, 0),
      lat = "40.7128",
      lng = "-74.0060"
    )

    `when`(licensePlateStatusService.findByLicensePlate(licensePlate)).thenReturn(expectedResponse)

    val response = controller.processEvent(licensePlate)

    assertEquals(expectedResponse.licensePlate, response.licensePlate)
    assertEquals(expectedResponse.priceUntilNow, response.priceUntilNow)
    assertEquals(expectedResponse.entryTime, response.entryTime)
    assertEquals(expectedResponse.timeParked, response.timeParked)
    assertEquals(expectedResponse.lat, response.lat)
    assertEquals(expectedResponse.lng, response.lng)
  }

}