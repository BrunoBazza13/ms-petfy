package br.com.petfy.ms_clinica.dto.veterinatiodto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record VeterinarioCadastroRequest(@NotBlank
                                          String nome,

                                         @NotBlank
                                          String crmv,

                                         @NotBlank
                                          String uf,

                                         @NotNull
                                         UUID usuarioId) {
}
