package br.com.petfy.ms_pet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TutorRequest(@NotBlank
                            String nome,
                           @NotNull
                           UUID usuarioId) {
}
