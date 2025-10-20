package br.com.petfy.ms_clinica.dto.veterinatiodto;

import br.com.petfy.ms_clinica.dto.clinica.ClinicaResponseDto;
import br.com.petfy.ms_clinica.dto.clinica.ClinicaVerificadaDTO;
import br.com.petfy.ms_clinica.model.Veterinario;

import java.util.UUID;

public record VeterinarioDetalhadoResponseDto(UUID id,
                                              String nome,
                                              ClinicaVerificadaDTO clinicaVerificadaDTO) {



}
