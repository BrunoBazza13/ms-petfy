package br.com.petfy.ms_procimento.client;

import br.com.petfy.ms_procimento.dto.ClinicaResponseDto;
import br.com.petfy.ms_procimento.dto.VeterinarioResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "MS-CLINICA")
public interface ClinicaClint {

    @GetMapping("/clinica/{id}")
    ClinicaResponseDto bucarClinicaPorid(@PathVariable UUID id);

    @GetMapping("/clinica/by-ids")
    List<ClinicaResponseDto> findClinicasByIds(@RequestParam List<UUID> ids);

    @GetMapping("/veterinario/by-ids")
    List<VeterinarioResponseDto> findVeterinariosByIds(@RequestParam List<UUID> ids);

    @GetMapping("/veterinario/{id}")
    VeterinarioResponseDto buscarVeterinarioPorId(@PathVariable UUID id);


}
