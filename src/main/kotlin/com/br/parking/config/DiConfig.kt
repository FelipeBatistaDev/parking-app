package com.br.parking.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class DiConfig {
  @Bean
  fun restTemplate(): RestTemplate = RestTemplate()
}