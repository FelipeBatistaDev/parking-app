package com.br.parking.service.calculator

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Duration
import java.time.LocalDateTime
import kotlin.math.ceil

@Service
class PriceCalculatorService {
  private val log = LoggerFactory.getLogger(PriceCalculatorService::class.java)

  fun calculatePrice(calculatePrice: PriceCalculatorInput): BigDecimal {
    val entryTime = calculatePrice.entryTime
    val exitTime = calculatePrice.exitTime
    val differenceInMinutes = findDifferenceInMinutes(entryTime, exitTime)

    log.info("Calculando tempo de permanência no estacionamento, tempo: {} minutos", differenceInMinutes)

    if(differenceInMinutes <= 30){
      return BigDecimal.ZERO
    }

    val charge = calculatePrice.charge
    val basePrice = calculatePrice.basePrice

    val priceWithoutCharge = findPriceWithoutCharge(basePrice, differenceInMinutes)

    log.info("Calculando preço final sem taxas, preço: {}", priceWithoutCharge)

    val chargePrice = findChargePrice(priceWithoutCharge, charge)

    log.info("Inserindo taxa, porcentagem: {}, valor da taxa aplicada: {}", charge, chargePrice)

    return calcularValorComPorcentagem(priceWithoutCharge, charge)
  }

  private fun calcularValorComPorcentagem(priceWithoutCharge: BigDecimal, charge: BigDecimal): BigDecimal {
    val fator = charge.divide(BigDecimal(100))
    return priceWithoutCharge.multiply(BigDecimal.ONE + fator).setScale(2, RoundingMode.HALF_UP)
  }

  private fun findDifferenceInMinutes(entryTime: LocalDateTime, exitTime: LocalDateTime): Long {
    return Duration.between(entryTime, exitTime).toMinutes()
  }

  private fun findPriceWithoutCharge(basePrice: BigDecimal, durationInMinutes: Long): BigDecimal {
    val hours = ceil(durationInMinutes / 60.0).toLong()
    return basePrice.multiply(BigDecimal(hours))
  }

  private fun findChargePrice(priceWithoutCharge: BigDecimal, charge: BigDecimal): BigDecimal {
    return priceWithoutCharge.multiply(charge).setScale(2, RoundingMode.UP)
  }
}