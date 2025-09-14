package br.com.petfy.ms_pet.dto;

import java.util.UUID;

public record PetResponseDto(  UUID id,
                               String nome,
                               String porte,
                               int idade,
                               char sexo,
                               double peso,
                               RacaDto raca,
                               int tutorID// DTO da Raça aninhado
                               ) {


}
