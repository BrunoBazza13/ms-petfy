package br.com.petfy.ms_pet.dto;

import java.util.UUID;

public record RacaDto(UUID id,
                      String nome,
                      EspecieDto especie) {
}
