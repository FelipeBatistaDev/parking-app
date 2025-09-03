package com.br.parking.shared.error

import org.springframework.http.HttpStatus

class RevenueNotFoundErrorException : CustomException(
  "There is not found data for revenue for date and sector informed",
  HttpStatus.NOT_FOUND
)
