package com.br.parking.infrastructure.repository.spot

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "tb_spot")
class SpotEntity (

  @Id
  @Column(name = "idt_spot")
  var id: Int = 0,

  @Column(name = "cod_sector")
  var sector: String = "",

  @Column(name = "num_lat")
  var lat: String = "",

  @Column(name = "num_lng")
  var lng: String = "",

  @Column(name = "bol_occupied")
  var occupied: Boolean = false
)