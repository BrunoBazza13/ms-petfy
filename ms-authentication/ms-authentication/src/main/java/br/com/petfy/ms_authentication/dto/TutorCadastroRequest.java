package br.com.petfy.ms_authentication.dto;

import br.com.petfy.ms_authentication.model.UserRole;

public record TutorCadastroRequest(String nome,
                                   String login,
                                   String password,
                                   UserRole role) {
}
