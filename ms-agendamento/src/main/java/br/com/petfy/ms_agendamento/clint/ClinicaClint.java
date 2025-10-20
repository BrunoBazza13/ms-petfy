package br.com.petfy.ms_agendamento.clint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@FeignClient(name = "MS-CLINICA")
public interface ClinicaClint {

    @PutMapping("/clinica/{id}/agendar")
    ResponseEntity<Void> agendarHorario(@PathVariable UUID id);

    @GetMapping("/clinica/agenda/by-ids")
    List<AgendaDetalhadaDTO> findAgendasByIds(@RequestParam List<UUID> ids);


    // Este é o DTO principal, agora "plano"
    @JsonIgnoreProperties(ignoreUnknown = true)
    record AgendaDetalhadaDTO(
            UUID id,
            LocalDateTime dataHoraInicio,
            VeterinarioDTO veterinario,
            ClinicaDTO clinica
    ) {}

    // DTO do veterinário (sem a clínica dentro)
    @JsonIgnoreProperties(ignoreUnknown = true)
    record VeterinarioDTO(UUID id, String nome) {}

    // DTO da clínica
    @JsonIgnoreProperties(ignoreUnknown = true)
    record ClinicaDTO(String nome) {}

}
