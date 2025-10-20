package br.com.petfy.ms_agendamento.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AgendamentoDetalhadoDto(UUID idAgendamento,
                                      br.com.petfy.ms_agendamento.model.StatusAgendamento status,
                                      LocalDateTime dataHora,
                                      String nomePet,
                                      String nomeVeterinario,
                                      String nomeClinica) {
}
