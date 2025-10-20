package br.com.petfy.ms_procimento.dto;

import br.com.petfy.ms_procimento.model.StatusGuia;

import java.util.UUID;

public record GuiaResponseDto(UUID id,
                              String tipoGuia,   // "VACINA" ou "EXAME"
                              String nomeProcedimento,
                              StatusGuia status// "Vacina V10" ou "Hemograma Completo"
)  {
   // PENDENTE, AGENDADO, REALIZADO) {
}
