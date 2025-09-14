package br.com.petfy.ms_clinica.service;

import br.com.petfy.ms_clinica.dto.*;
import br.com.petfy.ms_clinica.model.Clinica;
import br.com.petfy.ms_clinica.model.Endereco;
import br.com.petfy.ms_clinica.repository.ClinicaRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
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

        // 1️⃣ Consulta a API de CNPJ
        String resultadoJsonString = consultarCnpj(resquestDTO.cnpj());

       // System.out.println(resultadoJsonString);

        ObjectMapper objectMapper = new ObjectMapper();

        List<ClinicaResponseDto> apiResponse = objectMapper.readValue(
                resultadoJsonString,
                new TypeReference<List<ClinicaResponseDto>>() {}
        );

        // 2️⃣ Pega os dados do primeiro resultado
        ClinicaResponseDto dadosCrus = apiResponse.get(0);

        // 3️⃣ Valida situação cadastral
        if (!"ATIVA".equalsIgnoreCase(dadosCrus.situacaoCadastral())) {
            throw new RuntimeException("O CNPJ informado não está ativo. Cadastro não permitido: " + dadosCrus.cnpj());
        }

        // 4️⃣ Cria a entidade Clinica
        Clinica clinica = new Clinica();
        clinica.setNome(resquestDTO.nome());
        clinica.setCnpj(resquestDTO.cnpj());

        String fullAddress = String.format("%s, %s - %s, %s",
                resquestDTO.endereco().getLogradouro(),
                resquestDTO.endereco().getNumero(),
                resquestDTO.endereco().getCidade(),
                resquestDTO.endereco().getUf());

        Coordinates coords = geocodingService.getCoordinatesFromAddress(fullAddress);

        // Converte o endereço do DTO para a entidade
        Endereco endereco = new Endereco();
        BeanUtils.copyProperties(resquestDTO.endereco(), endereco);
        endereco.setLatitude(coords.latitude());
        endereco.setLongitude(coords.longitude());
        clinica.setEndereco(endereco);

        // 5️⃣ Salva no banco
        clinica = clinicaRepository.save(clinica);

        // 6️⃣ Retorna o DTO de resposta verificada
        return new ClinicaVerificadaDTO(clinica, dadosCrus.situacaoCadastral());

    }

}
