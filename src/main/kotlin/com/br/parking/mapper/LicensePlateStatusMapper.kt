package com.br.parking.mapper

import com.br.parking.api.response.LicensePlateStatusResponse
import com.br.parking.infrastructure.repository.licensePlateStatus.LicensePlateStatusOutput
import java.math.BigDecimal

object LicensePlateStatusMapper {

  fun toResponse(licensePlateStatusOutput: LicensePlateStatusOutput, price: BigDecimal?): LicensePlateStatusResponse{
    return LicensePlateStatusResponse(
      licensePlate = licensePlateStatusOutput.licensePlate,
      lat = licensePlateStatusOutput.lat.toString(),
      lng = licensePlateStatusOutput.lng.toString(),
      timeParked = licensePlateStatusOutput.timeParked,
      entryTime = licensePlateStatusOutput.entryTime,
      priceUntilNow = price
    )
  }
}