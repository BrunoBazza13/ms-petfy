package br.com.petfy.ms_pet.dto;

import br.com.petfy.ms_pet.model.Tutor;

import java.util.UUID;

public record PetResponseDto(UUID id,
                             String nome,
                             String porte,
                             int idade,
                             char sexo,
                             double peso,
                             RacaDto raca
                             // DTO da Raça aninhado
                               ) {

    public PetResponseDto(UUID id, String nome) {
        // CORRETO: Chama o construtor principal, passando os valores recebidos
        // e 'null' ou valores padrão para os campos restantes.
        this(id, nome, null, 0, '\u0000', 0.0, null);
    }

}
