package br.com.petfy.ms_validaCrmv.service;


import br.com.petfy.ms_validaCrmv.dto.CrmvRequest;
import br.com.petfy.ms_validaCrmv.dto.InfoSimplesApiResponseDTO;
import br.com.petfy.ms_validaCrmv.dto.DadosCrmvDTO;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ValidacaoService {


    @Autowired
    private RestTemplate restTemplate;

    @Value("${infosimples.api.url}")
    private String apiUrl;

    @Value("${infosimples.api.token}")
    private String apiToken;


    public String consultarCrmv(CrmvRequest request) throws IOException {

        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(apiUrl);
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("tipo_inscricao", String.valueOf(request.tipoInscricao())));
        params.add(new BasicNameValuePair("uf", request.uf()));
        params.add(new BasicNameValuePair("query", request.crmv()));
        params.add(new BasicNameValuePair("token", apiToken));
        params.add(new BasicNameValuePair("timeout", "300"));

        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));



        HttpResponse httpResponse = httpclient.execute(httppost);


        HttpEntity entity = httpResponse.getEntity();
        String body = EntityUtils.toString(entity, "UTF-8");
        JSONObject responseJson = new JSONObject(body);

        System.out.println(responseJson);

        if (responseJson.getInt("code") == 200) {
            // Se a requisição foi um sucesso, retorna o array de dados como String JSON
            JSONArray dataArray = responseJson.getJSONArray("data");

            return dataArray.toString(2);

        } else if (responseJson.getInt("code") >= 600 && responseJson.getInt("code") <= 799) {
            // --- BLOCO DE ERRO ATUALIZADO CONFORME SEU EXEMPLO ---

            // 1. Constrói a mensagem de erro detalhada
            String mensagem = "Um erro aconteceu. Leia para saber mais:\n";
            mensagem += "Código: " + responseJson.getInt("code") + " (" + responseJson.getString("code_message") + ")\n";

            // 2. Adiciona os erros específicos, se existirem
            if (!responseJson.isNull("errors")) {
                mensagem += "Detalhes: " + responseJson.getJSONArray("errors").join("; ");
            }

            // 3. Retorna a mensagem de erro formatada como uma String
            return mensagem;

        } else {
            // Bloco adicional para lidar com outros códigos de erro inesperados
            return "Ocorreu um erro inesperado com a API. Código: " + responseJson.getInt("code");
        }
    }
}
