package br.com.petfy.ms_clinica.controller;

import br.com.petfy.ms_clinica.dto.ClinicaResquestDTO;
import br.com.petfy.ms_clinica.dto.ClinicaVerificadaDTO;
import br.com.petfy.ms_clinica.service.ClinicaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("clinica")
public class ClinicaController {

    @Autowired
    private ClinicaService  clinicaService;

    @PostMapping("/test")
    public ResponseEntity<String> consultaCnpj(@RequestBody @Valid ClinicaResquestDTO resquest) throws IOException {

          String resultadoJson = clinicaService.consultarCnpj(resquest.cnpj());

          return ResponseEntity.ok()
                  .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                  .body(resultadoJson);

    }

    @PostMapping("/cnpj")
    public ResponseEntity<?> cadastraClinica(@RequestBody @Valid ClinicaResquestDTO resquest) throws IOException {

        ClinicaVerificadaDTO resultadoJson = clinicaService.cadastrarClinica(resquest);

        return ResponseEntity.ok(resultadoJson);

    }


}
