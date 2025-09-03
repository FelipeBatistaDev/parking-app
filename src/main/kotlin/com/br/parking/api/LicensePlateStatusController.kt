package com.br.parking.api

import com.br.parking.api.response.LicensePlateStatusResponse
import com.br.parking.service.LicensePlateStatusService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "Plate Status")
@RestController
@RequestMapping("plate-status")
class LicensePlateStatusController (
  private val licenseStatusService:LicensePlateStatusService
){

  @GetMapping
  @Operation(summary = "Retorna status pela placa")
  @ResponseStatus(HttpStatus.OK)
  fun processEvent(
    @Parameter(description = "Placa do veículo", example = "ZUL0001")
    @RequestParam("license_plate") licensePlate: String
  ): LicensePlateStatusResponse {
    return licenseStatusService.findByLicensePlate(licensePlate)
  }
}