package br.com.petfy.ms_agendamento.dto;

import br.com.petfy.ms_agendamento.model.Agendamento;

import java.util.UUID;

public record AgendamentoIdResponseDto(UUID id,
                                       UUID petId,
                                       UUID clinicaId,
                                       UUID veterinarioId) {

    public AgendamentoIdResponseDto(Agendamento agendamento) {
        this(agendamento.getId(), agendamento.getPetId(), agendamento.getClinicaId(), agendamento.getVeterinarioId());

    }
}
