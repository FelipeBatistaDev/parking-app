package com.br.parking.shared.error

import org.springframework.http.HttpStatus

class SpotStatusGenericErrorException(
  cause: Exception
) : CustomException(
  "There is a not mapped error in spot status context",
  HttpStatus.INTERNAL_SERVER_ERROR,
  cause
)
