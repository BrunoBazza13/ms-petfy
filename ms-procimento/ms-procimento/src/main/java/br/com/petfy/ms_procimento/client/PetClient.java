package br.com.petfy.ms_procimento.client;

import br.com.petfy.ms_procimento.dto.ClinicaResponseDto;
import br.com.petfy.ms_procimento.dto.PetResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "MS-PET")
public interface PetClient {

    @GetMapping("/pets/{id}")
    ResponseEntity<PetResponseDto> buscarPetPorId(@PathVariable UUID id);

    @GetMapping("/clinica/by-ids")
    List<PetResponseDto> findPetsByIds(@RequestParam List<UUID> ids);


}
