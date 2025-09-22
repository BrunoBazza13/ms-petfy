package br.com.petfy.ms_authentication.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(name = "MS-CLINICA")
public interface ClinicaClient {

    @PostMapping("/clinica/veterinarios")
    ResponseEntity<Void> criarPerfilVeterinario(VeterinarioProfileRequest request);
    record VeterinarioProfileRequest(String nome, String crmv, String uf, UUID usuarioId) {}

}
