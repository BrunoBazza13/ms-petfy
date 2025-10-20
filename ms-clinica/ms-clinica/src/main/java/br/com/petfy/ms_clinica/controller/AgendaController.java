package br.com.petfy.ms_clinica.controller;

import br.com.petfy.ms_clinica.dto.agenda.AgendaRequestDTO;
import br.com.petfy.ms_clinica.service.AgendaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("agendas")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @PostMapping("/gerar-horarios")
    public ResponseEntity<String> gerarHorarios(@RequestBody @Valid AgendaRequestDTO request) {
        agendaService.gerarHorarios(request);
        return ResponseEntity.ok("Agenda gerada com sucesso!");
    }

}
