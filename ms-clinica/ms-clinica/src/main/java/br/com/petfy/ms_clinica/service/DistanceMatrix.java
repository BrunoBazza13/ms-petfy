package br.com.petfy.ms_clinica.service;
import br.com.petfy.ms_clinica.dto.distance.DistanceMatrixResponse;
import br.com.petfy.ms_clinica.model.Clinica;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DistanceMatrix {

    @Value("${google.maps.api.key}")
    private String apiKey;

    private static final String GOOGLE_API_URL_DISTANCE_MATRIX = "https://maps.googleapis.com/maps/api/distancematrix/json";

    private final RestTemplate restTemplate = new RestTemplate();

    public DistanceMatrixResponse getDistanceMatrix(String originCoords, List<Clinica> clinicas) {

        if (clinicas.isEmpty()) {
            return null;
        }

        // Formata os destinos no padrão da API: "lat1,lon1|lat2,lon2|..."
        String destinations = clinicas.stream()
                .map(clinica -> clinica.getEndereco().getLatitude() + "," + clinica.getEndereco().getLongitude())
                .collect(Collectors.joining("|"));

        long timestamp = System.currentTimeMillis();

        String url = UriComponentsBuilder.fromHttpUrl(GOOGLE_API_URL_DISTANCE_MATRIX)
                .queryParam("origins", originCoords)
                .queryParam("destinations", destinations)
                .queryParam("key", apiKey)
                .queryParam("units", "metric")
                .queryParam("timestamp", timestamp)
                .build()
                .toUriString();

        return restTemplate.getForObject(url, DistanceMatrixResponse.class);
    }

}
