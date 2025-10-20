package br.com.petfy.ms_agendamento.controller;

import br.com.petfy.ms_agendamento.dto.AgendamentoDetalhadoDto;
import br.com.petfy.ms_agendamento.dto.AgendamentoIdResponseDto;
import br.com.petfy.ms_agendamento.dto.AgendamentoResponseDto;
import br.com.petfy.ms_agendamento.dto.AgendamentoResquestDto;
import br.com.petfy.ms_agendamento.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoResponseDto> criarAgendamento(
            @RequestBody @Valid AgendamentoResquestDto dto,
            @RequestHeader("X-User-ID") String tutorUsuarioId
    ) {
        UUID tutorId = UUID.fromString(tutorUsuarioId);
        AgendamentoResponseDto novoAgendamento = agendamentoService.criarAgendamento(dto, tutorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAgendamento);
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoDetalhadoDto>> listarAgendamentos(
            @RequestHeader("X-User-ID") String tutorUsuarioId
    ) {
        UUID tutorId = UUID.fromString(tutorUsuarioId);
        List<AgendamentoDetalhadoDto> agendamentos = agendamentoService.listaAgendamentos(tutorId);
        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoIdResponseDto> buscaVeterinarioPorId(@PathVariable UUID id) {

        AgendamentoIdResponseDto agendamentoIdResponseDto = agendamentoService.buscaAgendamentoPorID(id);

        System.out.println("--- Detalhes do Agendamento ---");
        System.out.println("ID do Agendamento: " + agendamentoIdResponseDto.id());
        System.out.println("ID do Pet: " + agendamentoIdResponseDto.petId());
        System.out.println("ID da Clínica: " + agendamentoIdResponseDto.clinicaId());
        System.out.println("ID do Veterinário: " + agendamentoIdResponseDto.veterinarioId());
        System.out.println("-----------------------------");


        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoIdResponseDto);
    }

}
