package com.br.parking.shared.error

import org.springframework.http.HttpStatus

class SpotStatusNotFoundErrorException : CustomException(
  "There is not found spot data for lat and lng informed",
  HttpStatus.NOT_FOUND
)
