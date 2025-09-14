package br.com.petfy.ms_authentication.dto;

import br.com.petfy.ms_authentication.model.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
