package br.com.petfy.ms_validaCrmv.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ApiResponse(@JsonProperty("site_receipt") String siteReceipt,
                          List<DadosCrmvDTO> resultados) {
}
