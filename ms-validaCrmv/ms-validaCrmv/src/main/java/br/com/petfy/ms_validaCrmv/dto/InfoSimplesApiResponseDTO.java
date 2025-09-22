package br.com.petfy.ms_validaCrmv.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record InfoSimplesApiResponseDTO(@JsonProperty("site_receipt")
                                          String siteReceipt,

                                        // O nome do campo "resultados" estava correto!
                                        List<DadosCrmvDTO> resultados ) {
}
