package com.br.parking.service

import com.br.parking.infrastructure.repository.licensePlateStatus.LicensePlateStatusOutput
import com.br.parking.infrastructure.repository.licensePlateStatus.LicensePlateStatusRepository
import com.br.parking.service.calculator.PriceCalculatorService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.test.Test

private lateinit var repository: LicensePlateStatusRepository
private lateinit var priceCalculatorService: PriceCalculatorService
private lateinit var service: LicensePlateStatusService

class LicensePlateStatusServiceTest {

  @BeforeEach
  fun setUp() {
    repository = mock(LicensePlateStatusRepository::class.java)
    priceCalculatorService = mock(PriceCalculatorService::class.java)
    service = LicensePlateStatusService(repository, priceCalculatorService)
  }

  @Test
  @DisplayName("Retorna informações do veículo pela placa fornecida com preço calculado")
  fun process_FindByPlate_Return_Success() {
    val licensePlate = "ABC1234"
    val licensePlateStatus = LicensePlateStatusOutput(
      licensePlate = licensePlate,
      parklotStatus = "ACTIVE",
      lat = 10.0,
      lng = 20.0,
      entryTime = LocalDateTime.of(2024, 6, 1, 10, 0),
      timeParked = LocalDateTime.of(2024, 6, 1, 12, 0),
      charge = BigDecimal("10.00"),
      basePrice = BigDecimal("5.00"),
      duration = 120
    )
    `when`(repository.findLicensePlateStatus(licensePlate)).thenReturn(licensePlateStatus)
    `when`(priceCalculatorService.calculatePrice(any())).thenReturn(BigDecimal("25.00"))

    val response = service.findByLicensePlate(licensePlate)

    assertEquals("ABC1234", response.licensePlate)
    assertEquals(BigDecimal("25.00"), response.priceUntilNow)
  }


  @Test
  @DisplayName("Consulta quando o veículo ainda está estacionado sem preço definido")
  fun process_FindByPlate_Return_Null() {
    val licensePlate = "ABC1234"
    val licensePlateStatus = LicensePlateStatusOutput(
      licensePlate = licensePlate,
      parklotStatus = "ACTIVE",
      lat = 10.0,
      lng = 20.0,
      entryTime = LocalDateTime.of(2024, 6, 1, 10, 0),
      timeParked = null,
      charge = BigDecimal("10.00"),
      basePrice = BigDecimal("5.00"),
      duration = 120
    )
    `when`(repository.findLicensePlateStatus(licensePlate)).thenReturn(licensePlateStatus)

    val response = service.findByLicensePlate(licensePlate)


    assertEquals("ABC1234", response.licensePlate)
    assertEquals(null, response.priceUntilNow)
  }
}
