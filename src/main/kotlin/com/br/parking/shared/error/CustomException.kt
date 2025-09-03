package com.br.parking.shared.error

import org.springframework.http.HttpStatus

open class CustomException(
  override val message: String,
  val status: HttpStatus,
  val exception: Exception = Exception(message)
) : RuntimeException(message, exception)