package com.br.parking.service

import com.br.parking.api.response.LicensePlateStatusResponse
import com.br.parking.infrastructure.repository.licensePlateStatus.LicensePlateStatusOutput
import com.br.parking.infrastructure.repository.licensePlateStatus.LicensePlateStatusRepository
import com.br.parking.mapper.LicensePlateStatusMapper
import com.br.parking.service.calculator.PriceCalculatorInput
import com.br.parking.service.calculator.PriceCalculatorService
import com.br.parking.shared.error.LicensePlateStatusNotFoundErrorException
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class LicensePlateStatusService(
  private val repository: LicensePlateStatusRepository,
  private val priceCalculatorService: PriceCalculatorService
) {

  fun findByLicensePlate(licensePlate: String): LicensePlateStatusResponse {
    val licensePlateStatus = repository
      .findLicensePlateStatus(licensePlate) ?: throw LicensePlateStatusNotFoundErrorException()

    if(licensePlateStatus.timeParked != null){
      val price: BigDecimal = calculatePrice(licensePlateStatus)
      return LicensePlateStatusMapper.toResponse(licensePlateStatus, price)
    }

      return LicensePlateStatusMapper.toResponse(licensePlateStatus, null)
  }

  private fun calculatePrice(licensePlateStatus: LicensePlateStatusOutput): BigDecimal {
     val calculatePrice = PriceCalculatorInput(
        entryTime = licensePlateStatus.entryTime!!,
        exitTime = licensePlateStatus.timeParked!!,
        basePrice = licensePlateStatus.basePrice,
        charge = licensePlateStatus.charge!!,
        durationLimitMinutes = licensePlateStatus.duration,
     )

    return priceCalculatorService.calculatePrice(calculatePrice);
  }
}