package br.com.petfy.ms_pet.contoller;

import br.com.petfy.ms_pet.dto.PetResponseDto;
import br.com.petfy.ms_pet.dto.PetResquestDto;
import br.com.petfy.ms_pet.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public ResponseEntity<PetResponseDto> cadastrarPedido(@RequestBody PetResquestDto petDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(petService.cadastrarPet(petDto));
    }

    @GetMapping
    public List<PetResponseDto> obterTodos() {
        return petService.listarTodosPets();
    }

    @GetMapping("/response")
    public String obterPorta(@Value("${local.server.port}") String porta) {
        return String.format("Resposta vinda da porta %s", porta);

    }
}
