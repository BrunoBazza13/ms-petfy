package br.com.petfy.ms_clinica.controller;


import br.com.petfy.ms_clinica.dto.Coordinates;
import br.com.petfy.ms_clinica.service.GeocodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test/geocoding")
public class GeocodingTestController {

    @Autowired
    private GeocodingService geocodingService;

    /**
     * Endpoint de teste para converter um endereço em coordenadas.
     * @param address O endereço completo a ser geocodificado, passado como um parâmetro na URL.
     * @return As coordenadas (latitude e longitude) ou uma mensagem de erro.
     */
    @GetMapping("/address")
    public ResponseEntity<?> testGeocodeAddress(@RequestParam String address) {
        try {
            // 1. Chama o serviço que você quer testar
            Coordinates coordinates = geocodingService.getCoordinatesFromAddress(address);

            // 2. Se funcionar, retorna o objeto de coordenadas com status 200 OK.
            //    O Spring vai converter o objeto 'coordinates' para JSON automaticamente.
            return ResponseEntity.ok(coordinates);

        } catch (Exception e) {
            // 3. Se o GeocodingService lançar um erro (ex: endereço não encontrado),
            //    capturamos e retornamos uma resposta de erro 400 Bad Request.
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
