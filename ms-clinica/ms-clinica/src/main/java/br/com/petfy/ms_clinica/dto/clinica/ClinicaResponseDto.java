package br.com.petfy.ms_clinica.dto.clinica;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ClinicaResponseDto(@JsonProperty("razao_social") String razaoSocial,
                                 @JsonProperty("nome_fantasia") String nomeFantasia,
                                 @JsonProperty("situacao_cadastral") String situacaoCadastral,
                                 String cnpj,
                                 @JsonProperty("endereco_logradouro") String logradouro,
                                 @JsonProperty("endereco_numero") String numero,
                                 @JsonProperty("endereco_complemento") String complemento,
                                 @JsonProperty("endereco_bairro") String bairro,
                                 @JsonProperty("endereco_municipio") String cidade,
                                 @JsonProperty("endereco_uf") String uf,
                                 @JsonProperty("endereco_cep") String cep){
                                 // Adicione outros campos que você queira capturar do JSON) {

}