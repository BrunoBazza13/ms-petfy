package br.com.petfy.ms_procimento.dto;

import java.util.UUID;

public record PetResponseDto(UUID id,
                             String nome,
                             String porte,
                             int idade,
                             char sexo,
                             double peso,
                             RacaResponseDto raca) {




}
