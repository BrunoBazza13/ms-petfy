package br.com.petfy.ms_clinica.dto.clinica;

import br.com.petfy.ms_clinica.model.Endereco;

public record ClinicaResquestDTO(String nome,
                                 String cnpj,
                                 Endereco endereco) {
}
