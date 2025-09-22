package br.com.petfy.ms_authentication.controller;


import br.com.petfy.ms_authentication.dto.AuthenticationDTO;
import br.com.petfy.ms_authentication.dto.LoginResponseDTO;
import br.com.petfy.ms_authentication.dto.VeterinarioCadastroRequest;
import br.com.petfy.ms_authentication.infra.security.TokenService;
import br.com.petfy.ms_authentication.model.Usuario;
import br.com.petfy.ms_authentication.service.UsuarioServiceTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationControllerTest {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioServiceTest usuarioServiceT;

    /*RF 02: O sistema deve validar que a senha tenha pelo menos 8 caracteres,
    contendo letra maiúscula, minúscula, número e caractere especial

    RF 03: O sistema deve permitir que o tutor faça login com e-mail e senha.

    RF 26: O sistema deve permitir o controle de acesso individualizado para cada veterinário da clínica

    */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {

        var userNamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(userNamePassword);

        var token = tokenService.genereteToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    /*RF 27: O sistema deve permitir o cadastro do veterinario informando nome, e-mail, senha, crmv e UF.
    * RF 15: O CRMV informado no cadastro da clínica deve ser validado antes da criação
    * */
    @PostMapping("/cadastro/veterinario")
    public ResponseEntity<String> cadastrarVeterinario(@RequestBody @Valid VeterinarioCadastroRequest dados) throws JsonProcessingException {
        usuarioServiceT.cadastrarVeterinario(dados);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
