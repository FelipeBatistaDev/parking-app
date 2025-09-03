package com.br.parking.service

import com.br.parking.infrastructure.repository.sector.SectorEntity
import com.br.parking.infrastructure.repository.sector.SectorRepository
import com.br.parking.shared.error.SectorGenericErrorException
import org.springframework.stereotype.Service

@Service
class SectorService(
  private val sectorRepository: SectorRepository
) {

  fun saveSector(sector: SectorEntity) {
    try {
      sectorRepository.save(sector)
    } catch (e: Exception) {
      throw SectorGenericErrorException(e)
    }
  }

  fun findSectorById(id: String): SectorEntity? {
    return try {
      sectorRepository.findById(id).orElse(null)
    } catch (e: Exception) {
      throw SectorGenericErrorException(e)
    }
  }

}