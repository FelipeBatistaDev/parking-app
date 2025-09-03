package com.br.parking.api

import com.br.parking.api.request.ParkLotRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.context.ApplicationContext
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Webhook")
@RestController
@RequestMapping("webhook")
class WebhookController(
  private val applicationContext: ApplicationContext
) {

  @PostMapping
  @Operation(summary = "Recebe eventos do webHook")
  fun processWebhookEvent(@RequestBody request: ParkLotRequest){
    val clazz = request.eventType.clazz
    val service = applicationContext.getBean(clazz)
    service.process(request)
  }
}