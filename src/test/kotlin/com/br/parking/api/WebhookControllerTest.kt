package com.br.parking.api

import com.br.parking.api.request.ParkLotRequest
import com.br.parking.service.SpotStatusService
import com.br.parking.service.webhook.WebhookService
import com.br.parking.shared.enums.EventTypeEnum
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito.*
import org.springframework.context.ApplicationContext
import java.time.LocalDateTime
import kotlin.test.Test

class WebhookControllerTest{
  private var applicationContext: ApplicationContext = mock()
  private var controller = WebhookController(applicationContext)

  @Test
  @DisplayName("POST evento webhook chamando serviço correto sucesso")
  fun processWebhookEvent_Returns200_WhenRequestIsValid() {
    val request = ParkLotRequest(
      licensePlate = "XYZ9876",
      entryTime = LocalDateTime.of(2023, 10, 1, 9, 0),
      exitTime = null,
      latitude = "37.7749",
      longitude = "-122.4194",
      eventType = EventTypeEnum.PARKED
    )
    val service = mock(WebhookService::class.java)
    `when`(applicationContext.getBean(EventTypeEnum.PARKED.clazz)).thenReturn(service)

    controller.processWebhookEvent(request)

    verify(service).process(request)
  }

}