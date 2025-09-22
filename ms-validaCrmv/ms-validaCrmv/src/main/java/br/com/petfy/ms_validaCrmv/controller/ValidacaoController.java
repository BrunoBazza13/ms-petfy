package br.com.petfy.ms_validaCrmv.controller;

import br.com.petfy.ms_validaCrmv.dto.CrmvRequest;
import br.com.petfy.ms_validaCrmv.dto.DadosCrmvDTO;
import br.com.petfy.ms_validaCrmv.service.ValidacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("validacoes")
public class ValidacaoController {

    @Autowired
    private ValidacaoService validacaoService;

    // Endpoint que outros microserviços vão chamar
    @PostMapping("/crmv")
    public ResponseEntity<String> validarCrmv(@RequestBody CrmvRequest request) throws IOException {

        // 1. Chama o serviço, que retorna a "caixa" (Optional)
       String resultadoOpt = validacaoService.consultarCrmv(request);

        // 2. Usa métodos do Optional para lidar com os dois cenários
        return ResponseEntity.ok(resultadoOpt); // 4. Se a caixa está vazia, .orElse() é executado
    }


}
