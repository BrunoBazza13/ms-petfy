package br.com.petfy.ms_pet.contoller;

import br.com.petfy.ms_pet.dto.PetResponseDto;
import br.com.petfy.ms_pet.dto.PetResquestDto;
import br.com.petfy.ms_pet.dto.TutorRequest;
import br.com.petfy.ms_pet.service.PetService;
import br.com.petfy.ms_pet.service.TutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private TutorService tutorService;


    /*RF 07: O tutor pode cadastrar um ou mais pets, com nome, espécie, raça, peso, porte, comportamento, idade e alergias
    *
    * RF 28: O sistema deve validar se todos os campos para o casdastro do pet estão preenchidos corretamente
    * */


    @PostMapping
    public ResponseEntity<PetResponseDto> cadastrarPet(@RequestBody @Valid PetResquestDto petDto,
                                                       @RequestHeader("X-User-ID") String tutorIdStr) {

        UUID tutorId = UUID.fromString(tutorIdStr);

        PetResponseDto petSalvo = petService.cadastrarPet(petDto, tutorId);

        return ResponseEntity.status(HttpStatus.CREATED).body(petSalvo);
    }


    @PostMapping("/tutores")
    public ResponseEntity<Void> criarPerfilTutor(@RequestBody @Valid TutorRequest tutorRequest) {
        // O usuarioId já vem dentro do DTO, enviado pelo ms-authentication
        tutorService.criarPerfil(tutorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public PetResponseDto buscarPorId(@PathVariable UUID id) {
        return petService.bucarPetPorId(id);

    }

    @GetMapping("/by-ids")
    public List<PetResponseDto> obterTodos() {
        return petService.listarTodosPets();
    }

    @GetMapping("/response")
    public String obterPorta(@Value("${local.server.port}") String porta) {
        return String.format("Resposta vinda da porta %s", porta);

    }
}
