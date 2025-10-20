package br.com.petfy.ms_clinica.dto.clinica;

import br.com.petfy.ms_clinica.model.Clinica;

import java.util.UUID;

public record ClinicaNomeDto(UUID id,
                             String nome) {

    public ClinicaNomeDto(Clinica clinica) {
        this(clinica.getId(), clinica.getNome());
    }
}
