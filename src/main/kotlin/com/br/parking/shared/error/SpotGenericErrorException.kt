package com.br.parking.shared.error

import org.springframework.http.HttpStatus

class SpotGenericErrorException(
  cause: Exception
) : CustomException(
  "There is a not mapped error in Spot context",
  HttpStatus.INTERNAL_SERVER_ERROR,
  cause
)
