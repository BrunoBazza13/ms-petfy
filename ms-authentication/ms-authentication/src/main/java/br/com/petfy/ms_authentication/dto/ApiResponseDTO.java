package br.com.petfy.ms_authentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ApiResponseDTO(@JsonProperty("site_receipt") String siteReceipt,
                             List<VeterinarioResponseDTO> resultados) {
}
