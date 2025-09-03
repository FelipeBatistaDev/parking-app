package com.br.parking.service

import com.br.parking.infrastructure.repository.parklot.ParkLotEntity
import com.br.parking.infrastructure.repository.parklot.ParkLotRepository
import com.br.parking.shared.enums.ParkLotStatusEnum
import com.br.parking.shared.error.ParkLotGenericErrorException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class ParkLotServiceTest{

  private lateinit var parkLotRepository: ParkLotRepository
  private lateinit var parkLotService: ParkLotService


  @BeforeEach
  fun setUp() {
    parkLotRepository = mock(ParkLotRepository::class.java)
    parkLotService = ParkLotService(parkLotRepository)
  }

  @Test
  @DisplayName("Salvar entidade no estacionamento com sucesso")
  fun process_Save_ParkLot_Success() {
    val parkLotEntity = ParkLotEntity(
      licensePlate = "ABC1234",
      status = ParkLotStatusEnum.ACTIVE
    )
    `when`(parkLotRepository.save(parkLotEntity)).thenReturn(parkLotEntity)

    val result = parkLotService.save(parkLotEntity)

    assertEquals("ABC1234", result.licensePlate)
    assertEquals(ParkLotStatusEnum.ACTIVE, result.status)
  }

  @Test
  @DisplayName("Salvar entidade no estacionamento com falha")
  fun process_Save_ParkLot_Error() {
    val parkLotEntity = ParkLotEntity(
      status = ParkLotStatusEnum.PARKED
    )
    `when`(parkLotRepository.save(parkLotEntity)).thenThrow(RuntimeException("Erro ao salvar"))

    assertThrows<ParkLotGenericErrorException> {
      parkLotService.save(parkLotEntity)
    }
  }

  @Test
  @DisplayName("Buscar entidade por placa e status com sucesso")
  fun process_FindByPlate_ParkLot_Success() {
    val licensePlate = "LMN4567"
    val status = ParkLotStatusEnum.PARKED
    val parkLotEntity = ParkLotEntity(
      licensePlate = licensePlate,
      status = status
    )
    `when`(parkLotRepository.findByLicensePlateAndStatus(licensePlate, status)).thenReturn(parkLotEntity)

    val result = parkLotService.findByPlateAndStatus(licensePlate, status)

    assertEquals(licensePlate, result?.licensePlate)
    assertEquals(status, result?.status)
  }

  @Test
  @DisplayName("Buscar entidade por placa e status retornando nulo")
  fun process_FindByPlate_ParkLot_And_Return_Null() {
    val licensePlate = "NOTFOUND"
    val status = ParkLotStatusEnum.EXITED
    `when`(parkLotRepository.findByLicensePlateAndStatus(licensePlate, status)).thenReturn(null)

    val result = parkLotService.findByPlateAndStatus(licensePlate, status)

    assertNull(result)
  }

  @Test
  @DisplayName("Buscar entidade por placa e status com falha e lança exceção")
  fun process_FindByPlate_ParkLot_And_Throws_Exception() {
    val licensePlate = "UNEXPECTED"
    val status = ParkLotStatusEnum.ACTIVE
    `when`(parkLotRepository.findByLicensePlateAndStatus(licensePlate, status))
      .thenThrow(IllegalStateException("Erro inesperado"))

    assertThrows<ParkLotGenericErrorException> {
      parkLotService.findByPlateAndStatus(licensePlate, status)
    }
  }


}