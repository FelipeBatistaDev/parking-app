package com.br.parking.shared.error

import java.time.LocalDateTime

data class CustomError(
  val message: String,
  val timestamp: LocalDateTime
)