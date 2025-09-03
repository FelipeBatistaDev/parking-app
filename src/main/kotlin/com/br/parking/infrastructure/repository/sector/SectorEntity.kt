package com.br.parking.infrastructure.repository.sector

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalTime

@Entity
@Table(name = "tb_sector")
data class SectorEntity (
  @Id
  @Column(name = "idt_sector", length = 1)
  var sector: String = "",

  @Column(name = "num_base_price")
  var  basePrice: BigDecimal? = null,

  @Column(name = "num_max_capacity")
  var maxCapacityInteger: Int = 0,

  @Column(name = "hor_open_hour")
  var openHour: LocalTime? = null,

  @Column(name = "hor_close_hour")
  var closeHour: LocalTime? = null,

  @Column(name = "num_duration_limit_minutes")
  var durationLimitMinutes: Int = 0
)