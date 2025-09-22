package br.com.petfy.ms_authentication.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosCrmvDTO(String nome,
                           String situacao,
                           String crmv,
                           String uf,
                           String area,
                           String inscricao,

                           // Mapeia os nomes com "_" para o padrão Java camelCase
                           @JsonProperty("data_inscricao")
                           String dataInscricao,

                           @JsonProperty("razao_social")
                           String razaoSocial,

                           @JsonProperty("dados_art")
                           String dadosArt,

                           String responsavel,

                           @JsonProperty("nome_fantasia")
                           String nomeFantasia) {
}
