package br.com.petfy.ms_clinica.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GeocodingResult(@JsonProperty("formatted_address") String formattedAddress,
                              Geometry geometry) {
}
