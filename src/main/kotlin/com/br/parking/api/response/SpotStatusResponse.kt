package com.br.parking.api.response

data class SpotStatusResponse(
  val id: Int,
  val sector: String,
  val lat: String,
  val lng: String,
  val occupied: Boolean
)
