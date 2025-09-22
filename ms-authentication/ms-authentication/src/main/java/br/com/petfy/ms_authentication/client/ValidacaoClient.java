package br.com.petfy.ms_authentication.client;

import br.com.petfy.ms_authentication.dto.DadosCrmvDTO;
import br.com.petfy.ms_authentication.dto.VeterinarioResponseDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "MS-VALIDACRMV")
public interface ValidacaoClient {

    @PostMapping("/validacoes/crmv")
    ResponseEntity<String> validarCrmv(CrmvRequest request);

    //colocar role ao inves de int
    record CrmvRequest(String crmv, String uf, int tipoInscricao)  {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    record VeterinarioResponseDTO(
            String nome,
            String situacao,
            String crmv,
            String uf
            // Adicione outros campos que o ms-validacao retorna, se precisar deles
    ){}

}
