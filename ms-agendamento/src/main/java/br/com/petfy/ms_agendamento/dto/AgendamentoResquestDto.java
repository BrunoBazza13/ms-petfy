package br.com.petfy.ms_agendamento.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AgendamentoResquestDto(@NotNull UUID clinicaId,      // <-- ID DA CLÍNICA ADICIONADO
                                     @NotNull UUID veterinarioId,  // <-- ID DO VETERINÁRIO ADICIONADO
                                     @NotNull UUID agendaId,       // ID do horário no ms-clinica
                                     @NotNull UUID petId,          // ID do pet no ms-pet
                                     String motivo) {
}
