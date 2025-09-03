package com.br.parking.api

import com.br.parking.api.request.SpotStatusRequest
import com.br.parking.api.response.SpotStatusResponse
import com.br.parking.service.SpotStatusService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import kotlin.test.Test

class SpotStatusControllerTest{

  private val spotService: SpotStatusService = mock()
  private val controller = SpotStatusController(spotService)

  @Test
  @DisplayName("Get Spot Status retornando sucesso")
  fun processEvent_ReturnsSpotStatusResponse_WhenLatAndLngAreValid() {
    val lat = "-23.561684"
    val lng = "-46.655981"
    val expectedResponse = SpotStatusResponse(
      id = 1,
      sector = "A",
      lat = lat,
      lng = lng,
      occupied = true
    )

    `when`(spotService.findByLatLng(SpotStatusRequest(lat, lng))).thenReturn(expectedResponse)

    val response = controller.processEvent(lat, lng)

    assertEquals(expectedResponse.id, response.id)
    assertEquals(expectedResponse.sector, response.sector)
    assertEquals(expectedResponse.lat, response.lat)
    assertEquals(expectedResponse.lng, response.lng)
    assertEquals(expectedResponse.occupied, response.occupied)
  }
}