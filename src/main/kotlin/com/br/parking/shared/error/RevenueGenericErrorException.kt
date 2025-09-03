package com.br.parking.shared.error

import org.springframework.http.HttpStatus

class RevenueGenericErrorException(
  cause: Exception
) : CustomException(
  "There is a not mapped error in revenue context",
  HttpStatus.INTERNAL_SERVER_ERROR,
  cause
)
