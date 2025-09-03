package com.br.parking.config

import com.br.parking.infrastructure.client.GarageClient
import com.br.parking.mapper.GarageMapper
import com.br.parking.service.SectorService
import com.br.parking.service.SpotService
import com.br.parking.service.webhook.LeaveParkLotService
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class GarageStartup(
  private val garageClient: GarageClient,
  private val sectorService: SectorService,
  private val spotService: SpotService
) : ApplicationRunner {
  private val log = LoggerFactory.getLogger(LeaveParkLotService::class.java)

  override fun run(args: ApplicationArguments) {
    log.info("Carregando dados da garagem")

    val response = garageClient.findGarage()

    log.info("Dados carregados com sucesso")

    response?.garage?.forEach { dto ->
      sectorService.saveSector(GarageMapper.toSector(dto))
    }

    response?.spots?.forEach { dto ->
      spotService.saveSpot(GarageMapper.toSpot(dto))
    }
  }
}