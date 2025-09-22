package br.com.petfy.ms_authentication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AuthenticationDTO(
                                @NotBlank(message = "Email é obrigatório")
                                @Email(message = "Formato de email inválido")
                                String login,

                                @NotBlank(message = "Senha é obrigatória")
                                @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
                                        message = "A senha deve ter no mínimo 8 caracteres, contendo letra maiúscula, minúscula, número e um caractere especial.")
                                String password) {
}
