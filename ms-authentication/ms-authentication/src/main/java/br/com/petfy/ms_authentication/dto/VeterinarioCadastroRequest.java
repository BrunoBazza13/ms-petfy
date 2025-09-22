package br.com.petfy.ms_authentication.dto;

import br.com.petfy.ms_authentication.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record VeterinarioCadastroRequest(@NotBlank(message = "Nome é obrigatório")
                                         String nome,

                                         @NotBlank(message = "Email é obrigatório")
                                         @Email(message = "Formato de email inválido")
                                         String login,

                                         @NotBlank(message = "Senha é obrigatória")
                                         @Pattern(
                                                 regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
                                                 message = "A senha deve ter no mínimo 8 caracteres, contendo letra maiúscula, minúscula, número e um caractere especial."
                                         )
                                         String password,

                                         UserRole role,

                                         @NotBlank(message = "CRMV é obrigatório")
                                         String crmv,

                                         @NotBlank(message = "UF do CRMV é obrigatória")
                                         @Size(min = 2, max = 2, message = "UF deve ter 2 caracteres")
                                         String uf,
                                         int tipoInscricao) {
}
