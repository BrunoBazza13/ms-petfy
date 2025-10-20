package br.com.petfy.ms_clinica.dto.agenda;

import br.com.petfy.ms_clinica.dto.clinica.ClinicaVerificadaDTO;
import br.com.petfy.ms_clinica.dto.veterinatiodto.VeterinarioResponseDTO;
import br.com.petfy.ms_clinica.model.Agenda;
import br.com.petfy.ms_clinica.model.Status;

import java.time.LocalDateTime;
import java.util.UUID;

public record AgendaDetalhadaDto(UUID id,
                                 LocalDateTime  dataHoraInicio,
                                 Status  status,
                                 VeterinarioResponseDTO veterinario,
                                 ClinicaVerificadaDTO clinica) {

    public AgendaDetalhadaDto(Agenda agenda) {
        this(
                agenda.getId(),
                agenda.getDataHoraInicio(),
                agenda.getStatus(),
                new VeterinarioResponseDTO(agenda.getVeterinario()),
                new ClinicaVerificadaDTO(agenda.getVeterinario().getClinicas().stream().findFirst().orElse(null), null)
        );
    }
}
