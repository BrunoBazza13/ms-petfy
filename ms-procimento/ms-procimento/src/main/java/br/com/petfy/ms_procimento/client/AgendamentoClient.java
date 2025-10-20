package br.com.petfy.ms_procimento.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "MS-AGENDAMENTO")
public interface AgendamentoClient {

    @GetMapping("/agendamentos/{id}")
    ResponseEntity<AgendamentoDTO> findAgendamentoById(@PathVariable UUID id);

    // DTO "espelho" com os dados do agendamento que nos interessam
    record AgendamentoDTO(
            UUID id,
            UUID petId,
            UUID tutorId,
            UUID veterinarioId, // Supondo que o agendamento saiba o ID do veterinário
            UUID clinicaId      // Supondo que o agendamento saiba o ID da clínica
    ) {}

}
