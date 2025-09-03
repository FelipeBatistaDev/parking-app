package com.br.parking.shared.error

import org.springframework.http.HttpStatus

class SpotsNotAvailableException: CustomException(
  "Não foram encontrados vagas disponíveis",
  HttpStatus.NOT_FOUND,
)
