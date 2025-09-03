package com.br.parking.infrastructure.client

data class SpotDTO(
  val id: Int,
  val sector: String,
  val lat: String,
  val lng: String,
  val occupied: Boolean
)
