package com.br.parking.infrastructure.repository.spot

import org.springframework.data.jpa.repository.JpaRepository

interface SpotRepository: JpaRepository<SpotEntity, Int> {
  fun findFirstByOccupied(occupied: Boolean): SpotEntity?
  fun countByOccupied(occupied: Boolean): Int
   fun findByLatAndLng(lat: String, lng: String): SpotEntity?
}