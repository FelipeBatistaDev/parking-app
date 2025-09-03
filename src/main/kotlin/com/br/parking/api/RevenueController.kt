package com.br.parking.api

import com.br.parking.api.request.RevenueRequest
import com.br.parking.api.response.RevenueResponse
import com.br.parking.service.RevenueService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@Tag(name = "Revenue")
@RestController
@RequestMapping("revenue")
class RevenueController(
  private val revenueService: RevenueService
) {

  @GetMapping
  @Operation(summary = "Retorna valor arrecadado por setor e data")
  @ResponseStatus(HttpStatus.OK)
  fun process(
    @Parameter(description = "Setor da garagem", example = "A")
    @RequestParam sector: String,

    @Parameter(description = "Data do faturamento", example = "2025-01-01")
    @RequestParam date: LocalDate
  ): RevenueResponse {
    val request = RevenueRequest(sector, date)
    return revenueService.findByDateAndSector(request)
  }
}