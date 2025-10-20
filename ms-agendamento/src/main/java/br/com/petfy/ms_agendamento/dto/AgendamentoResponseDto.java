package br.com.petfy.ms_agendamento.dto;

import br.com.petfy.ms_agendamento.model.Agendamento;
import br.com.petfy.ms_agendamento.model.StatusAgendamento;

import java.time.LocalDateTime;
import java.util.UUID;

public record AgendamentoResponseDto(UUID id,
                                     UUID agendaId,
                                     UUID petId,
                                     UUID tutorId,
                                     StatusAgendamento status){

    public AgendamentoResponseDto(Agendamento agendamento) {
        this(agendamento.getId(), agendamento.getAgendaId(), agendamento.getPetId(), agendamento.getTutorId(), agendamento.getStatus());
    }
                                    // LocalDateTime dataCriacao) {
}
