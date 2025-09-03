package com.br.parking.shared.error

import org.springframework.http.HttpStatus

class LicensePlateStatusGenericErrorException(
  cause: Exception
) : CustomException(
  "There is a not mapped error in license status context",
  HttpStatus.INTERNAL_SERVER_ERROR,
  cause
)
