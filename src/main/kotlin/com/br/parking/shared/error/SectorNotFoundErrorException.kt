package com.br.parking.shared.error

import org.springframework.http.HttpStatus

class SectorNotFoundErrorException : CustomException(
  "There is not found data for sector for cod informed",
  HttpStatus.NOT_FOUND
)
