package com.br.parking.infrastructure.client

import com.br.parking.shared.error.GarageConfigFailedErrorException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.client.RestTemplate

@Component
class GarageClient(
  private val restTemplate: RestTemplate,
  @Value("\${garage.api.url}") private val garageApiUrl: String
) {
  val logger = LoggerFactory.getLogger(GarageClient::class.java)

  fun findGarage(): GarageOutput? {
    return try {
      restTemplate.getForObject(garageApiUrl, GarageOutput::class.java)
    } catch (e: Exception) {
      logger.error("Fail to retrieve garage config from api /garage: {}", e.message)
      throw GarageConfigFailedErrorException()
    }
  }
}
