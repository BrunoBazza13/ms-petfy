package br.com.petfy.ms_authentication.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record VeterinarioResponseDTO(
        String area,
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
        String nomeFantasia
) {}