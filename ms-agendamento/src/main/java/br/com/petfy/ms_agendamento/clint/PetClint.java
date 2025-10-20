package br.com.petfy.ms_agendamento.clint;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "MS-PET")
public interface PetClint {

    @GetMapping("/pets/by-ids")
    List<PetDTO> findPetsByIds(@RequestParam List<UUID> ids);

    // DTO "espelho" com os dados do pet que nos interessam
    record PetDTO(UUID id, String nome) {}

}
