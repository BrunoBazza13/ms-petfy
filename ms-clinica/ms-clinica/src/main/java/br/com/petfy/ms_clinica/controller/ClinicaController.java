package br.com.petfy.ms_clinica.controller;

import br.com.petfy.ms_clinica.dto.agenda.AgendaDetalhadaDto;
import br.com.petfy.ms_clinica.dto.agenda.AgendaRequestDTO;
import br.com.petfy.ms_clinica.dto.agenda.AgendaResponseDTO;
import br.com.petfy.ms_clinica.dto.clinica.*;
import br.com.petfy.ms_clinica.dto.veterinatiodto.VeterinarioCadastroRequest;
import br.com.petfy.ms_clinica.repository.VeterinarioRepository;
import br.com.petfy.ms_clinica.service.AgendaService;
import br.com.petfy.ms_clinica.service.ClinicaService;
import br.com.petfy.ms_clinica.service.VeterinarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/clinica")
public class ClinicaController {

    @Autowired
    private ClinicaService  clinicaService;

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @Autowired
    private VeterinarioService veterinarioService;

    /*
    * RF 14: O veterinário responsável pode cadastrar uma clínica informando nome da clínica, cnpj e endereço
    * RF 29:
    * */

    @PostMapping("/cnpj")
    public ResponseEntity<?> cadastraClinica(@RequestBody @Valid ClinicaResquestDTO resquest,
                                             @RequestHeader("X-User-ID") String usuarioIdStr) throws IOException {

        UUID usuarioId = UUID.fromString(usuarioIdStr);

        ClinicaVerificadaDTO resultadoJson = clinicaService.cadastrarClinica(resquest,  usuarioId);

        return ResponseEntity.ok(resultadoJson);

    }

    /*
    * RF 17: O veterinário responsável pode adicionar membros à clínica gerando acesso individual (e-mail/senha)

    * */

    @PostMapping("/veterinarios") // jogar para veterinariocontrooler
    public ResponseEntity<Void> criarPerfilVeterinario(@RequestBody VeterinarioCadastroRequest dados) {
        veterinarioService.criarPerfil(dados);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /*
    * RF 21: O tutor pode visualizar detalhes da clínica (nome, veterinários, avaliações)
    * RF 20: O tutor pode buscar clínicas mais próximas pela sua localização
    * */
    @GetMapping("/proximas")
    public List<ClinicaProximaDTO> findNearbyClinics(
            @RequestParam double userLat,
            @RequestParam double userLon
    ) {
        return clinicaService.findNearbyRealDistance(userLat, userLon);
    }

    @PostMapping("/horarios")
    public ResponseEntity<String> gerarHorarios(@RequestBody @Valid AgendaRequestDTO request) {
        agendaService.gerarHorarios(request);
        return ResponseEntity.ok("Agenda gerada com sucesso!");
    }

    @PutMapping("/{id}/agendar")
    public ResponseEntity<Void> agendarHorario(@PathVariable UUID id) {
        agendaService.ocuparHorario(id);
        return ResponseEntity.ok().build(); // Retorna 200 OK sem corpo
    }


    @GetMapping("/disponibilidade/{veterinario}")
    public ResponseEntity<List<AgendaResponseDTO>> listarHorariosVagos(@PathVariable("veterinario") UUID veterinario) {
        List<AgendaResponseDTO> horarios = agendaService.buscarHorariosDisponiveis(veterinario);
        return ResponseEntity.ok(horarios);
    }

    @GetMapping("/agenda/by-ids") //mudar controller para agenda
    public ResponseEntity<List<AgendaDetalhadaDto>> findByIds(@RequestParam List<UUID> ids) {
        List<AgendaDetalhadaDto> agendas = agendaService.listarAgendamentosComDetalhes(ids);
        return ResponseEntity.ok(agendas);
    }


    @GetMapping("/by-ids")
    public ResponseEntity<List<ClinicaNomeDto>> findByClinicaIds(@RequestParam List<UUID> ids) {
        List<ClinicaNomeDto> agendas = clinicaService.listarClinicas(ids);
        return ResponseEntity.ok(agendas);
    }

    @GetMapping("/{id}")
    public ClinicaNomeDto buscarPorId(@PathVariable("id") UUID id) {
        return clinicaService.bucarClinicaPorId(id);
    }

}
