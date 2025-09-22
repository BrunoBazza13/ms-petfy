package br.com.petfy.ms_clinica.service;
import br.com.petfy.ms_clinica.dto.clinica.ClinicaProximaDTO;
import br.com.petfy.ms_clinica.dto.clinica.ClinicaResponseDto;
import br.com.petfy.ms_clinica.dto.clinica.ClinicaResquestDTO;
import br.com.petfy.ms_clinica.dto.clinica.ClinicaVerificadaDTO;
import br.com.petfy.ms_clinica.dto.coordinates.Coordinates;
import br.com.petfy.ms_clinica.dto.distance.Distance;
import br.com.petfy.ms_clinica.dto.distance.DistanceMatrixResponse;
import br.com.petfy.ms_clinica.dto.distance.Element;
import br.com.petfy.ms_clinica.model.Clinica;
import br.com.petfy.ms_clinica.model.Endereco;
import br.com.petfy.ms_clinica.repository.ClinicaRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.http.impl.client.HttpClients;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;


@Service
public class ClinicaService {

    @Value("${infosimples.api.url}")
    private String apiUrl;

    @Value("${infosimples.api.token}")
    private String apiToken;

    @Autowired
    private GeocodingService geocodingService;

    @Autowired
    private ClinicaRepository clinicaRepository;

    @Autowired
    private DistanceMatrix distanceMatrix;



    public String consultarCnpj(String cnpj) throws IOException {

        HttpClient httpClient= HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(apiUrl);
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("cnpj", cnpj));
        params.add(new BasicNameValuePair("token", apiToken));
        params.add(new BasicNameValuePair("timeout", "300"));

        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity entity = httpResponse.getEntity();
        String body = EntityUtils.toString(entity, "UTF-8");
        JSONObject responseJson = new JSONObject(body);

        if(responseJson.getInt("code") == 200){
            JSONArray dataArray = responseJson.getJSONArray("data");
            return dataArray.toString(2);

        } else if (responseJson.getInt("code") >= 600 && responseJson.getInt("code") <= 799) {

            String mensagem = "Um erro aconteceu. Leia para saber mais:\n";
            mensagem += "Código: " + responseJson.getInt("code") + " (" + responseJson.getString("code_message") + ")\n";

            if(!responseJson.isNull("errors")){
                mensagem += "Detalhes: " + responseJson.getJSONArray("errors").join("; ");
            }
            return mensagem;

        }else {
            return "Ocorreu um erro inesperado com a API. Código: " + responseJson.getInt("code");
         }
    }


    public ClinicaVerificadaDTO cadastrarClinica(ClinicaResquestDTO resquestDTO) throws IOException {

        String resultadoJsonString = consultarCnpj(resquestDTO.cnpj());

        ObjectMapper objectMapper = new ObjectMapper();

        List<ClinicaResponseDto> apiResponse = objectMapper.readValue(
                resultadoJsonString,
                new TypeReference<List<ClinicaResponseDto>>() {}
        );

        ClinicaResponseDto dadosCrus = apiResponse.get(0);

        if (!"ATIVA".equalsIgnoreCase(dadosCrus.situacaoCadastral())) {
            throw new RuntimeException("O CNPJ informado não está ativo. Cadastro não permitido: " + dadosCrus.cnpj());
        }

        Clinica clinica = new Clinica();
        clinica.setNome(resquestDTO.nome());
        clinica.setCnpj(resquestDTO.cnpj());

        String fullAddress = String.format("%s, %s - %s, %s",
                resquestDTO.endereco().getLogradouro(),
                resquestDTO.endereco().getNumero(),
                resquestDTO.endereco().getCidade(),
                resquestDTO.endereco().getUf());

        Coordinates coords = geocodingService.getCoordinatesFromAddress(fullAddress);

        Endereco endereco = new Endereco();
        BeanUtils.copyProperties(resquestDTO.endereco(), endereco);
        endereco.setLatitude(coords.latitude());
        endereco.setLongitude(coords.longitude());
        clinica.setEndereco(endereco);

        clinica = clinicaRepository.save(clinica);

        return new ClinicaVerificadaDTO(clinica, dadosCrus.situacaoCadastral());

    }

    public List<ClinicaProximaDTO> findNearbyRealDistance(double userLat, double userLon) {

        List<Clinica> todasClinicas = clinicaRepository.findAll();

        if (todasClinicas.isEmpty()) {
            return new ArrayList<>();
        }

        String originCoords = userLat + "," + userLon;
        DistanceMatrixResponse response = distanceMatrix.getDistanceMatrix(originCoords, todasClinicas);

        List<ClinicaProximaDTO> clinicasComDistancia = new ArrayList<>();

        if (response != null && "OK".equals(response.status()) && !response.rows().isEmpty()) {

            List<Element> elements = response.rows().get(0).elements();

            for (int i = 0; i < todasClinicas.size(); i++) {

                if (i < elements.size() && "OK".equals(elements.get(i).status())) {

                    Clinica clinica = todasClinicas.get(i);
                    Distance distance = elements.get(i).distance();

                    clinicasComDistancia.add(
                            new ClinicaProximaDTO(
                                    clinica.getNome(),
                                    clinica.getEndereco(),
                                    distance.text()
                            )
                    );
                }
            }
        }
        return clinicasComDistancia;
    }


}
