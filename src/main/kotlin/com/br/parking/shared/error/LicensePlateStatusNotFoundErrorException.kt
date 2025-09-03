package com.br.parking.shared.error

import org.springframework.http.HttpStatus

class LicensePlateStatusNotFoundErrorException : CustomException(
  "There is not found data for license plate informed",
  HttpStatus.NOT_FOUND
)
