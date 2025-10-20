package br.com.petfy.ms_clinica.dto.veterinatiodto;

import br.com.petfy.ms_clinica.model.Veterinario;

import java.util.UUID;

public record VeterinarioResponseDTO(UUID id,
                                     String nome,
                                     String crmv) {

    public VeterinarioResponseDTO(Veterinario veterinario) {
        this(veterinario.getId(), veterinario.getNome(), veterinario.getCrmv());
    }
}
