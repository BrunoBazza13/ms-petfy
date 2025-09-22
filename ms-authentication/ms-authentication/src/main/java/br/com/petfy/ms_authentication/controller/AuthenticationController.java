//package br.com.petfy.ms_authentication.controller;
//
//import br.com.petfy.ms_authentication.dto.*;
//import br.com.petfy.ms_authentication.infra.security.TokenService;
//import br.com.petfy.ms_authentication.model.Tutor;
//import br.com.petfy.ms_authentication.model.User;
//import br.com.petfy.ms_authentication.repository.UserRepository;
//import br.com.petfy.ms_authentication.service.UsuarioService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//@RequestMapping("auth")
//public class AuthenticationController {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private TokenService tokenService;
//
//    @Autowired
//    private UsuarioService crmvService;
//
//
//    @PostMapping("/login")
//    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
//
//        var userNamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
//        var auth = this.authenticationManager.authenticate(userNamePassword);
//
//        var token = tokenService.genereteToken((User) auth.getPrincipal());
//
//        return ResponseEntity.ok(new LoginResponseDTO(token));
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
//        if (this.userRepository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();
//
//        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
//        User newUser = new Tutor(data.login(), encryptedPassword, data.role());
//        this.userRepository.save(newUser);
//
//        return ResponseEntity.ok().build();
//    }
//
//
//    @PostMapping("/crmv")
//    public ResponseEntity<?> verificarCrmv(@RequestBody @Valid VeterinarioRegisterDTO request) {
//        try {
//            // 1. Chama o serviço que APENAS consulta a API externa
//            VeterinarioVerificadoDTO resultadoVerificado = crmvService.consultarCrmvNaApi(request);
//
//            // 2. Retorna o objeto DTO limpo. O Spring o converterá para JSON automaticamente.
//            return ResponseEntity.ok(resultadoVerificado);
//
//        } catch (RuntimeException e) {
//            // Se o CRMV não for encontrado ou estiver inativo, retorna 404
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        } catch (IOException e) {
//            // Erro de comunicação com a API externa
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro de comunicação com o serviço externo.");
//        }
//    }
//
////    @PostMapping("/test")
////    public ResponseEntity<String> testarConsultaCrmv(@RequestBody @Valid VeterinarioRequestDTO request) {
////        try {
////            // Chama o seu método de serviço, que retorna uma String
////            String resultadoJson = crmvService.consultarCrmv(request.crmv(),request.uf(), request.tipo_inscricao());
////
////            // Retorna a String diretamente.
////            // O ideal é checar se a string é um JSON para definir o Content-Type,
////            // mas para um teste, podemos retornar como JSON.
////            return ResponseEntity.ok()
////                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
////                    .body(resultadoJson);
////
////        } catch (Exception e) {
////            // Se o serviço lançar uma exceção (ex: erro de rede), o controller a captura
////            // e retorna uma mensagem de erro claro.
////            return ResponseEntity.badRequest().body(e.getMessage());
////        }
////    }
//}
//
//
