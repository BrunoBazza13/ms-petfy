package br.com.petfy.ms_clinica.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Location(
        @JsonProperty("lat") Double latitude,
        @JsonProperty("lng") Double longitude
) {
}
