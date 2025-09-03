package com.br.parking.shared.error

import org.springframework.http.HttpStatus

class FullGarageException: CustomException(
  "Garage is full, comeback later",
  HttpStatus.BAD_REQUEST,
)
