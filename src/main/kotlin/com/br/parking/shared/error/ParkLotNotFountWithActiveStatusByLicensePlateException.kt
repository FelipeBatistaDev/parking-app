package com.br.parking.shared.error

import org.springframework.http.HttpStatus

class ParkLotNotFountWithActiveStatusByLicensePlateException : CustomException(
  "There is a no park lot register for license plate with 'ACTIVE' status",
  HttpStatus.NOT_FOUND
)
