package com.br.parking.shared.error

import org.springframework.http.HttpStatus

class MandatoryParamsNotInformedException(message: String) : CustomException(
  message,
  HttpStatus.BAD_REQUEST
)
