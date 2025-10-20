package br.com.petfy.ms_procimento.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record ProntuarioRequestDto(@NotNull(message = "O ID do agendamento é obrigatório")
                                   UUID agendamentoId,
                                   // Campos clínicos preenchidos pelo veterinário
                                   String sinaisClinicos,
                                   String exameFisico,
                                   String diagnostico,
                                   String condutasRealizadas,
                                   String evolucaoClinica,
                                   String prescricao,
                                   // Listas com os IDs das vacinas e exames recomendados
                                   List<UUID> vacinasSolicitadasIds,
                                   List<UUID> examesSolicitadosIds) {
}
