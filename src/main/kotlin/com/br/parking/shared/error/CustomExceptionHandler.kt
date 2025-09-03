package com.br.parking.shared.error

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime

@Hidden
@ControllerAdvice
class CustomExceptionHandler {

  @ExceptionHandler(CustomException::class)
  fun handleCustomException(ex: CustomException): ResponseEntity<CustomError> {

    val customError = CustomError(
      message = ex.message,
      timestamp = LocalDateTime.now()
    )

    return ResponseEntity.status(ex.status).body(customError)
  }
}
