package br.com.petfy.ms_clinica.controller;

import br.com.petfy.ms_clinica.dto.clinica.ClinicaProximaDTO;
import br.com.petfy.ms_clinica.dto.clinica.ClinicaResquestDTO;
import br.com.petfy.ms_clinica.dto.clinica.ClinicaVerificadaDTO;
import br.com.petfy.ms_clinica.dto.veterinatiodto.VeterinarioCadastroRequest;
import br.com.petfy.ms_clinica.service.ClinicaService;
import br.com.petfy.ms_clinica.service.VeterinarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("clinica")
public class ClinicaController {

    @Autowired
    private ClinicaService  clinicaService;

    @Autowired
    private VeterinarioService veterinarioService;

    @PostMapping("/test")
    public ResponseEntity<String> consultaCnpj(@RequestBody @Valid ClinicaResquestDTO resquest) throws IOException {

          String resultadoJson = clinicaService.consultarCnpj(resquest.cnpj());

          return ResponseEntity.ok()
                  .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                  .body(resultadoJson);

    }


    /*
    * RF 14: O veterinário responsável pode cadastrar uma clínica informando nome da clínica, cnpj e endereço
    * RF 29:
    * */

    @PostMapping("/cnpj")
    public ResponseEntity<?> cadastraClinica(@RequestBody @Valid ClinicaResquestDTO resquest) throws IOException {

        ClinicaVerificadaDTO resultadoJson = clinicaService.cadastrarClinica(resquest);

        return ResponseEntity.ok(resultadoJson);

    }

    /*
    * RF 17: O veterinário responsável pode adicionar membros à clínica gerando acesso individual (e-mail/senha)

    * */

    @PostMapping("/veterinarios")
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

    @GetMapping("/response")
    public String obterPorta(@Value("${local.server.port}") String porta) {
        return String.format("Resposta vinda da porta %s", porta);

    }

}
