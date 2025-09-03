package com.br.parking.shared.enums

import com.br.parking.service.webhook.EntryParkLotService
import com.br.parking.service.webhook.EntrySpotService
import com.br.parking.service.webhook.LeaveParkLotService
import com.br.parking.service.webhook.WebhookService

enum class EventTypeEnum(
  val clazz: Class<out WebhookService>,
  val status: ParkLotStatusEnum
) {
  ENTRY(EntryParkLotService::class.java, ParkLotStatusEnum.ACTIVE),
  PARKED(EntrySpotService::class.java, ParkLotStatusEnum.PARKED),
  EXIT(LeaveParkLotService::class.java, ParkLotStatusEnum.EXITED)
}