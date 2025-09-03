package com.br.parking.service.webhook

import com.br.parking.api.request.ParkLotRequest

interface WebhookService {
    fun  process(request: ParkLotRequest): Unit
}