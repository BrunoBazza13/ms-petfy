package br.com.petfy.ms_authentication.service;

import br.com.petfy.ms_authentication.client.ClinicaClient;
import br.com.petfy.ms_authentication.client.ValidacaoClient;
import br.com.petfy.ms_authentication.dto.ApiResponseDTO;
import br.com.petfy.ms_authentication.dto.VeterinarioCadastroRequest;
import br.com.petfy.ms_authentication.dto.VeterinarioResponseDTO;
import br.com.petfy.ms_authentication.model.Usuario;
import br.com.petfy.ms_authentication.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ValidacaoClient validacaoClient;

    @Autowired
    private ClinicaClient clinicaClient;


    @Transactional
    public void cadastrarVeterinario(VeterinarioCadastroRequest dados) throws JsonProcessingException {

        // --- ETAPA 1: VALIDAR O CRMV CHAMANDO O MS-VALIDACAO ---
        System.out.println("Etapa 1: Validando CRMV...");
        var requestValidacao = new ValidacaoClient.CrmvRequest(dados.crmv(), dados.uf(), dados.tipoInscricao());
        ResponseEntity<String> responseValidacao;

        try {
            responseValidacao = validacaoClient.validarCrmv(requestValidacao);
            System.out.println(responseValidacao);

        } catch (Exception e) {
            // Se a chamada falhar (ex: ms-validacao fora do ar), lança um erro.
            throw new RuntimeException("Falha na comunicação com o serviço de validação: " + e.getMessage());
        }

                ObjectMapper objectMapper = new ObjectMapper();

        String jsonResponse = responseValidacao.getBody();

        List<ApiResponseDTO> apiResponse = objectMapper.readValue(
                jsonResponse,
                new TypeReference<List<ApiResponseDTO>>() {}
        );

        if (apiResponse.isEmpty() || apiResponse.get(0).resultados().isEmpty()) {
            throw new RuntimeException("CRMV não encontrado na consulta externa.");
        }

        VeterinarioResponseDTO dadosCrus = apiResponse.get(0).resultados().get(0);

        if (!"ativo".equalsIgnoreCase(dadosCrus.situacao())) {
            throw new RuntimeException("O CRMV informado não está ativo. Cadastro não permitido." + dadosCrus.crmv());

        }

        Usuario novoUsuario = new Usuario(dados.login(), dados.password(), dados.role());

        Usuario usuarioSalvo = userRepository.save(novoUsuario);
        System.out.println("Conta de acesso criada com ID: " + usuarioSalvo.getId());

        // --- ETAPA 3: CRIAR O PERFIL PROFISSIONAL CHAMANDO O MS-CLINICA ---
        System.out.println("Etapa 3: Solicitando criação do perfil profissional no ms-clinica...");
        var requestPerfil = new ClinicaClient.VeterinarioProfileRequest(
                dados.nome(),      // Usa o nome oficial validado
                dados.crmv(),      // Usa o CRMV validado
                dados.uf(),        // Usa a UF validada
                usuarioSalvo.getId()        // Envia o ID da conta de acesso recém-criada
        );

        try {
            clinicaClient.criarPerfilVeterinario(requestPerfil);
        } catch (Exception e) {
            // IMPORTANTE: Aqui temos um desafio de sistemas distribuídos.
            // O usuário foi salvo, mas a criação do perfil falhou.
            // Em um sistema real, usaríamos um padrão chamado "Saga" para desfazer a criação do usuário.
            // Por enquanto, lançamos uma exceção clara para indicar o estado inconsistente.
            throw new RuntimeException("ERRO CRÍTICO: A conta de acesso foi criada, mas falhou ao criar o perfil no ms-clinica. Causa: " + e.getMessage());
        }

        System.out.println("Perfil profissional solicitado com sucesso.");

    }

}
