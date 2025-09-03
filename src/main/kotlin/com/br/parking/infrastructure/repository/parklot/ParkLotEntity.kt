package com.br.parking.infrastructure.repository.parklot

import com.br.parking.shared.enums.ParkLotStatusEnum
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "tb_park_lot")
data class ParkLotEntity (
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idt_park_lot")
  val id: Long = 0,

  @Column(name = "des_license_plate", nullable = false)
  val licensePlate: String = "",

  @Column(name = "dtt_parked_time")
  val  parkedTime: LocalDateTime? = null,

  @Column(name = "dtt_exit_time")
  var exitTime: LocalDateTime? = null,

  @Column(name = "cod_spot")
  var spotId: Int = 0,

  @Column(name = "num_total_minutes")
  var totalMinutes: Int = 0,

  @Column(name = "num_amount_charge")
  var amountCharge: BigDecimal? = null,

  @Column(name = "num_final_price")
  var finalPrice: BigDecimal? = null,

  @Column(name = "num_base_price")
  var basePrice: BigDecimal? = null,

  @Column(name = "num_duration_limit_minutes")
  var durationLimitMinutes: Int = 0,

  @Enumerated(EnumType.STRING)
  @Column(name = "des_status")
  var status: ParkLotStatusEnum? = null
)