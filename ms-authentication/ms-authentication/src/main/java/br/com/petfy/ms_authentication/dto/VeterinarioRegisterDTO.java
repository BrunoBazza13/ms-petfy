package br.com.petfy.ms_authentication.dto;

public record VeterinarioRegisterDTO(String login,
                                     String password,
                                     String nomeCompleto,
                                     String crmv,
                                     String uf,
                                     int tipo_inscricao) {
}
