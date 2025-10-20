package br.com.petfy.ms_clinica.dto.agenda;
import br.com.petfy.ms_clinica.model.Agenda;
import br.com.petfy.ms_clinica.model.Status;


import java.time.LocalDateTime;
import java.util.UUID;

public record AgendaResponseDTO(UUID id,
                                LocalDateTime  dataHoraInicio,
                                Status  status
                               ) {

    public AgendaResponseDTO(Agenda agenda) {
        this(
                agenda.getId(),
                agenda.getDataHoraInicio(),
                agenda.getStatus()
        );
    }

}
