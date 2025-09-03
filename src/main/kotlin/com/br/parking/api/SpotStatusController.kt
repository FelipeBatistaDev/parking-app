package com.br.parking.api

import com.br.parking.api.request.SpotStatusRequest
import com.br.parking.api.response.SpotStatusResponse
import com.br.parking.service.SpotStatusService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "Spot Status")
@RestController
@RequestMapping("spot-status")
class SpotStatusController(
  private val spotStatusService: SpotStatusService
) {

  @GetMapping
  @Operation(summary = "Retorna status da vaga")
  @ResponseStatus(HttpStatus.OK)
  fun processEvent(
    @Parameter(description = "Data do faturamento", example = "-23.561684")
    @RequestParam lat: String,

    @Parameter(description = "Data do faturamento", example = "-46.655981")
    @RequestParam lng: String
  ): SpotStatusResponse {
    val request = SpotStatusRequest(lat, lng)
    return spotStatusService.findByLatLng(request)
  }
}