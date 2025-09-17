package br.com.petfy.ms_clinica.dto.clinica;

import br.com.petfy.ms_clinica.model.Endereco;

public record ClinicaProximaDTO(String nome, Endereco endereco, String distancia) {
}
