package com.br.parking.service.chargefactory

import java.math.BigDecimal

class TestCapacityResultFactory {

  companion object {
    fun lowerThan25Percent(): TestCapacityResultOutput {
      return TestCapacityResultOutput(
        charge = BigDecimal("-10"),
      )
    }

    fun lowerThan50Percent(): TestCapacityResultOutput {
      return TestCapacityResultOutput(
        charge = BigDecimal("0.0"),
      )
    }

    fun lowerThan75Percent(): TestCapacityResultOutput {
      return TestCapacityResultOutput(
        charge = BigDecimal("10"),
      )
    }

    fun lowerThan100Percent(): TestCapacityResultOutput {
      return TestCapacityResultOutput(
        charge = BigDecimal("25"),
      )
    }
  }
}
