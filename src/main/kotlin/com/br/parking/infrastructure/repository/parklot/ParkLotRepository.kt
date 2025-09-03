package com.br.parking.infrastructure.repository.parklot

import com.br.parking.shared.enums.ParkLotStatusEnum
import org.springframework.data.jpa.repository.JpaRepository

interface ParkLotRepository : JpaRepository<ParkLotEntity, Long> {
   fun findByLicensePlateAndStatus(licensePlate: String,  status: ParkLotStatusEnum): ParkLotEntity?
}