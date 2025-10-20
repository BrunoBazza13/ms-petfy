package br.com.petfy.ms_clinica.dto.agenda;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record AgendaRequestDTO(UUID veterinario,
                               List<LocalDate> datas,
                               LocalTime horaInicio,
                               LocalTime horaFim,
                               int duracaoConsultaEmMinutos) {
}
