package br.com.petfy.ms_procimento.controller;

import br.com.petfy.ms_procimento.dto.ProntuarioRequestDto;
import br.com.petfy.ms_procimento.dto.ProntuarioResponseDto;
import br.com.petfy.ms_procimento.service.ProntuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/prontuario")
public class ProntuarioController {

    @Autowired
    private ProntuarioService prontuarioService;

    @PostMapping
    public ResponseEntity<ProntuarioResponseDto> gerarProntuario(@RequestBody @Valid ProntuarioRequestDto prontuarioRequestDto,
                                                                 @RequestHeader("X-User-ID") String veterinarioIdStr){

        UUID veterinarioId = UUID.fromString(veterinarioIdStr);

        ProntuarioResponseDto prontuarioResponseDto = prontuarioService.gerarProntuarioEGuias(prontuarioRequestDto, veterinarioId);

        return ResponseEntity.status(HttpStatus.CREATED).body(prontuarioResponseDto);

    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<ProntuarioResponseDto>> buscarProntuariosPorPet(@PathVariable UUID petId) {
        List<ProntuarioResponseDto> prontuarios = prontuarioService.buscarProntuario(petId);
        return ResponseEntity.ok(prontuarios);
    }

}
