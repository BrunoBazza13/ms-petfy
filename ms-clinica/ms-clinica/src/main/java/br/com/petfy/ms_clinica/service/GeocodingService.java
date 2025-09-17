package br.com.petfy.ms_clinica.service;

import br.com.petfy.ms_clinica.dto.coordinates.Coordinates;
import br.com.petfy.ms_clinica.dto.coordinates.GeocodingResponse;
import br.com.petfy.ms_clinica.dto.coordinates.Location;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GeocodingService {

    @Value("${google.maps.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String GOOGLE_API_URL = "https://maps.googleapis.com/maps/api/geocode/json";


    public Coordinates getCoordinatesFromAddress(String address) {

        long timestamp = System.currentTimeMillis();

        String url = UriComponentsBuilder.fromHttpUrl(GOOGLE_API_URL)
                .queryParam("address", address)
                .queryParam("key", apiKey)
                .queryParam("language", "pt-BR")
                .queryParam("region", "BR")
                .queryParam("timestamp", timestamp)
                .build()
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String jsonBrutoRecebido = responseEntity.getBody();

        GeocodingResponse response = restTemplate.getForObject(url, GeocodingResponse.class);

        if (response == null || !"OK".equals(response.status()) || response.results().isEmpty()) {
            throw new RuntimeException("Não foi possível obter as coordenadas para o endereço: " + address);
        }

        Location location = response.results().get(0).geometry().location();

        return new Coordinates(location.latitude(), location.longitude());
    }

}
