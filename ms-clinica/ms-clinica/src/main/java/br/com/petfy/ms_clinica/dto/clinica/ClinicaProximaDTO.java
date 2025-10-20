package br.com.petfy.ms_clinica.dto.clinica;

import br.com.petfy.ms_clinica.dto.veterinatiodto.VeterinarioResponseDTO;
import br.com.petfy.ms_clinica.model.Endereco;
import br.com.petfy.ms_clinica.model.Veterinario;

import java.util.List;

public record ClinicaProximaDTO(String nome, Endereco endereco, List<VeterinarioResponseDTO> veterinarios, String distancia) {
}
