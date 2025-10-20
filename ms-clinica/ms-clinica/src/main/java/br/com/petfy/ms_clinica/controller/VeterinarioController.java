package br.com.petfy.ms_clinica.controller;

import br.com.petfy.ms_clinica.dto.agenda.AgendaDetalhadaDto;
import br.com.petfy.ms_clinica.dto.veterinatiodto.VeterinarioResponseDTO;
import br.com.petfy.ms_clinica.service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("veterinario")
public class VeterinarioController {

    @Autowired
    private VeterinarioService veterinarioService;

    @GetMapping("/{id}")
    public VeterinarioResponseDTO buscaVeterinarioPorId(@PathVariable("id") UUID id){
        return veterinarioService.buscarVeterinarioPorId(id);

    }

    @GetMapping("/by-ids")
    public ResponseEntity<List<VeterinarioResponseDTO>> findByIds(@RequestParam List<UUID> ids) {
        List<VeterinarioResponseDTO> agendas = veterinarioService.listarVeterinarios(ids);
        return ResponseEntity.ok(agendas);
    }

    @GetMapping
    public List<VeterinarioResponseDTO> listaDeVeterinarios(){
        return   veterinarioService.listarVeterinarios();
    }


}
