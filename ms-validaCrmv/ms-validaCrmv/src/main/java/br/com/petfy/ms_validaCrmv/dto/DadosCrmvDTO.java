package br.com.petfy.ms_validaCrmv.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DadosCrmvDTO(String area,
                           String vigencia,
                           String uf,
                           String situacao,
                           String inscricao,
                           String crmv,

                           @JsonProperty("data_inscricao")
                                  String dataInscricao,

                           @JsonProperty("razao_social")
                                  String razaoSocial,

                           @JsonProperty("dados_art")
                                  String dadosArt,

                           String nome,
                           String responsavel,

                           @JsonProperty("nome_fantasia")
                                  String nomeFantasia) {
}
