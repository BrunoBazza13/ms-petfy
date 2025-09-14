package br.com.petfy.ms_clinica.service;

import br.com.petfy.ms_clinica.dto.Coordinates;
import br.com.petfy.ms_clinica.dto.GeocodingResponse;
import br.com.petfy.ms_clinica.dto.Location;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GeocodingService {

    @Value("${google.maps.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String GOOGLE_API_URL = "https://maps.googleapis.com/maps/api/geocode/json";

    /**
     * Converte um endereço em texto para coordenadas geográficas (Latitude e Longitude).
     * @param address O endereço completo (Ex: "Avenida Paulista, 1000, São Paulo, SP")
     * @return Um objeto Coordinates com a latitude e longitude.
     */
    public Coordinates getCoordinatesFromAddress(String address) {

        // 1. Constrói a URL de forma segura com o UriComponentsBuilder.
        // Ele cuida automaticamente da "codificação de URL" mencionada na documentação.
        String url = UriComponentsBuilder.fromHttpUrl(GOOGLE_API_URL)
                .queryParam("address", address)
                .queryParam("key", apiKey)
                .queryParam("language", "pt-BR") // Parâmetro opcional: resultados em português
                .queryParam("region", "BR")      // Parâmetro opcional: prioriza resultados no Brasil
                .toUriString();

        // 2. Chama a API do Google e mapeia a resposta para nosso DTO GeocodingResponse
        GeocodingResponse response = restTemplate.getForObject(url, GeocodingResponse.class);

        System.out.println(response);

        // 3. Verifica se a resposta é válida e contém resultados
        if (response == null || !"OK".equals(response.status()) || response.results().isEmpty()) {
            throw new RuntimeException("Não foi possível obter as coordenadas para o endereço: " + address);
        }

        // 4. Extrai a latitude e a longitude do primeiro resultado encontrado
        Location location = response.results().get(0).geometry().location();

        // 5. Retorna um objeto simples com as coordenadas
        return new Coordinates(location.latitude(), location.longitude());
    }

}
