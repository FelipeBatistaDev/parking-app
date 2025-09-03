package com.br.parking.shared.error

import org.springframework.http.HttpStatus

class ParkLotGenericErrorException(
  cause: Exception
) : CustomException(
  "There is a not mapped error in Park Lot context",
  HttpStatus.INTERNAL_SERVER_ERROR,
  cause
)
