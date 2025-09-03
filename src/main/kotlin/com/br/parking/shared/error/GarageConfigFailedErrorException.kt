package com.br.parking.shared.error

import org.springframework.http.HttpStatus

class GarageConfigFailedErrorException : CustomException(
  "Fail to retrieve garage data",
  HttpStatus.INTERNAL_SERVER_ERROR
)
