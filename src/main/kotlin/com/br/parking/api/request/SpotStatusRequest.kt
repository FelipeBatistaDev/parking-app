package com.br.parking.api.request

import com.fasterxml.jackson.annotation.JsonProperty

data class SpotStatusRequest(

    @JsonProperty(value = "lat")
    val lat: String,

    @JsonProperty(value = "lng")
    val lng: String
)
