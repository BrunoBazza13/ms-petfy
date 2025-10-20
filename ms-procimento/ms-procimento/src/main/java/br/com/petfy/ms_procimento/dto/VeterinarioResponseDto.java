package br.com.petfy.ms_procimento.dto;

import java.util.UUID;

public record VeterinarioResponseDto(UUID id,
                                     String nome,
                                     String crmv) {
}
