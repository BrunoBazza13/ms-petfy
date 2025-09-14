package br.com.petfy.ms_clinica.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GeocodingResponse(String status,
                                @JsonProperty("results") List<GeocodingResult> results) {
}
