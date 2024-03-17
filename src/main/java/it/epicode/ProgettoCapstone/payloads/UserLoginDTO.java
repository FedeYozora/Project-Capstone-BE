package it.epicode.ProgettoCapstone.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(
        @Email(message = "E-mail format is not valid")
        @NotBlank(message = "Mail field cannot be empty")
        String email,
        @NotBlank(message = "Password field cannot be empty")
        String password) {
}
