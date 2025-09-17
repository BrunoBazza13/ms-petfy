package br.com.petfy.ms_clinica.dto.distance;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record DistanceMatrixResponse(@JsonProperty("destination_addresses") List<String> destinationAddresses,
                                     @JsonProperty("origin_addresses") List<String> originAddresses,
                                     List<Row> rows,
                                     String status) {
}
