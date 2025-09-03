package com.br.parking.shared.error

import org.springframework.http.HttpStatus

class SectorGenericErrorException(
  cause: Exception
) : CustomException(
  "There is a not mapped error in Sector context",
  HttpStatus.INTERNAL_SERVER_ERROR,
  cause
)
