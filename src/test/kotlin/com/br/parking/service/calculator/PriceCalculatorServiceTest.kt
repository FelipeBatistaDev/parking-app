package com.br.parking.service.calculator

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime

class PriceCalculatorServiceTest {

  private val service = PriceCalculatorService()

  @Test
  @DisplayName("Verifica se com menos de 30 minutos retorna 0 no preço")
  fun calculatePrice_ReturnsZero_WhenDurationIs30MinutesOrLess() {
    val input = PriceCalculatorInput(
      entryTime = LocalDateTime.of(2024, 6, 1, 10, 0),
      exitTime = LocalDateTime.of(2024, 6, 1, 10, 30),
      basePrice = BigDecimal("10.00"),
      charge = BigDecimal("0.0"),
      durationLimitMinutes = 60
    )
    val result = service.calculatePrice(input)
    assertEquals(BigDecimal.ZERO, result)
  }

  @Test
  @DisplayName("Verifica valor com 1 hora com aumento de taxa de 10%")
  fun calculatePrice_AppliesBasePriceAndIncreaseCharge_WhenDurationIsMoreThan30Minutes() {
    val input = PriceCalculatorInput(
      entryTime = LocalDateTime.of(2024, 6, 1, 10, 0),
      exitTime = LocalDateTime.of(2024, 6, 1, 11, 0),
      basePrice = BigDecimal("10.00"),
      charge = BigDecimal("10"),
      durationLimitMinutes = 60
    )

    val result = service.calculatePrice(input)
    assertEquals(BigDecimal("11.00"), result)
  }


  @Test
  @DisplayName("Verifica valor com 1 hora com desconto de taxa de 10%")
  fun calculatePrice_AppliesBasePriceAndDecreaseCharge_WhenDurationIsMoreThan30Minutes() {
    val input = PriceCalculatorInput(
      entryTime = LocalDateTime.of(2024, 6, 1, 10, 0),
      exitTime = LocalDateTime.of(2024, 6, 1, 11, 0),
      basePrice = BigDecimal("10.00"),
      charge = BigDecimal("-10"),
      durationLimitMinutes = 60
    )
    val result = service.calculatePrice(input)
    assertEquals(BigDecimal("9.00"), result)
  }

}