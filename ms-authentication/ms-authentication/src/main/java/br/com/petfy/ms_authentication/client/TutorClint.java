package br.com.petfy.ms_authentication.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "MS-PET")
public interface TutorClint {

    @PostMapping("/pets/tutores")
    ResponseEntity<Void> criarPerfilTutor(@RequestBody TutorRequest tutorCadastroRequest);
    record TutorRequest(String nome,  UUID usuarioId) {}

}
