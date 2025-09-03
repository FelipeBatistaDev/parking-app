package com.br.parking.infrastructure.repository.sector

import org.springframework.data.jpa.repository.JpaRepository

interface SectorRepository: JpaRepository<SectorEntity, String> {
}