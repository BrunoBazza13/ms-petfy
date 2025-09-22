//package br.com.petfy.ms_authentication.service;
//
//
//import br.com.petfy.ms_authentication.dto.*;
//import br.com.petfy.ms_authentication.model.UserRole;
//import br.com.petfy.ms_authentication.model.Veterinario;
//import br.com.petfy.ms_authentication.repository.UserRepository;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.validation.Valid;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//
//@Service
//public class UsuarioService {
//
//    @Value("${infosimples.api.url}")
//    private String apiUrl;
//
//    @Value("${infosimples.api.token}")
//    private String apiToken;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    public String consultarCrmv(String crmv, String uf, int tipoInscricao) throws IOException {
//
//        // A lógica da requisição continua a mesma
//        HttpClient httpclient = HttpClients.createDefault();
//        HttpPost httppost = new HttpPost(apiUrl);
//        List<NameValuePair> params = new ArrayList<>();
//        params.add(new BasicNameValuePair("tipo_inscricao", String.valueOf(tipoInscricao)));
//        params.add(new BasicNameValuePair("uf", uf));
//        params.add(new BasicNameValuePair("query", crmv));
//        params.add(new BasicNameValuePair("token", apiToken));
//        params.add(new BasicNameValuePair("timeout", "300"));
//
//        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
//
//        HttpResponse httpResponse = httpclient.execute(httppost);
//        HttpEntity entity = httpResponse.getEntity();
//        String body = EntityUtils.toString(entity, "UTF-8");
//        JSONObject responseJson = new JSONObject(body);
//
//        if (responseJson.getInt("code") == 200) {
//            // Se a requisição foi um sucesso, retorna o array de dados como String JSON
//            JSONArray dataArray = responseJson.getJSONArray("data");
//
//            return dataArray.toString(2);
//
//        } else if (responseJson.getInt("code") >= 600 && responseJson.getInt("code") <= 799) {
//            // --- BLOCO DE ERRO ATUALIZADO CONFORME SEU EXEMPLO ---
//
//            // 1. Constrói a mensagem de erro detalhada
//            String mensagem = "Um erro aconteceu. Leia para saber mais:\n";
//            mensagem += "Código: " + responseJson.getInt("code") + " (" + responseJson.getString("code_message") + ")\n";
//
//            // 2. Adiciona os erros específicos, se existirem
//            if (!responseJson.isNull("errors")) {
//                mensagem += "Detalhes: " + responseJson.getJSONArray("errors").join("; ");
//            }
//
//            // 3. Retorna a mensagem de erro formatada como uma String
//            return mensagem;
//
//        } else {
//            // Bloco adicional para lidar com outros códigos de erro inesperados
//            return "Ocorreu um erro inesperado com a API. Código: " + responseJson.getInt("code");
//        }
//    }
//
//    public VeterinarioVerificadoDTO consultarCrmvNaApi(VeterinarioRegisterDTO registerData) throws IOException {
//
//        if (userRepository.findByLogin(registerData.login()) != null) {
//            throw new RuntimeException("Este login (email) já está em uso.");
//        }
//
//        String resultadoJsonString = this.consultarCrmv(registerData.crmv(), registerData.uf(), registerData.tipo_inscricao());
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        List<ApiResponseDTO> apiResponse = objectMapper.readValue(
//                resultadoJsonString,
//                new TypeReference<List<ApiResponseDTO>>() {}
//        );
//
//        if (apiResponse.isEmpty() || apiResponse.get(0).resultados().isEmpty()) {
//            throw new RuntimeException("CRMV não encontrado na consulta externa.");
//        }
//
//        VeterinarioResponseDTO dadosCrus = apiResponse.get(0).resultados().get(0);
//
//        if (!"ativo".equalsIgnoreCase(dadosCrus.situacao())) {
//            throw new RuntimeException("O CRMV informado não está ativo. Cadastro não permitido." + dadosCrus.crmv());
//
//        }
//
//        // Etapa 5: Salvar o novo veterinário no banco de dados
//        String encryptedPassword = passwordEncoder.encode(registerData.password());
//        Veterinario novoVeterinario = new Veterinario(
//                registerData.login(),
//                encryptedPassword,
//                UserRole.ADMIN,
//                dadosCrus.nome(), // Usando o nome verificado da API
//                dadosCrus.crmv(),
//                dadosCrus.uf()
//        );
//            userRepository.save(novoVeterinario);
//
//        // Etapa 6: "Traduzir" os dados crus para o DTO limpo de resposta
//        VeterinarioVerificadoDTO dadosVerificados = new VeterinarioVerificadoDTO(
//                dadosCrus.nome(),
//                dadosCrus.crmv(),
//                dadosCrus.situacao(),
//                dadosCrus.uf(),
//                dadosCrus.area(),
//                dadosCrus.dataInscricao(),
//                registerData.login(),
//                encryptedPassword
//
//        );
//
//        // Etapa 7: Retornar o DTO limpo
//        return dadosVerificados;
//    }
//
//    public void cadastrarVeterinario(@Valid VeterinarioCadastroRequest dados) {
//
//    }
//}
//
//
