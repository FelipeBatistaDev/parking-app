package com.br.parking.infrastructure.client

data class GarageOutput(
  val garage: List<SectorDTO>,
  val spots: List<SpotDTO>
)
