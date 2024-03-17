package it.epicode.ProgettoCapstone.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewUserDTO(
        @NotBlank(message = "Username field cannot be empty")
        @Size(min = 3, max = 20, message = "Username's length must be between 3 and 20 characters")
        String username,
        @NotBlank(message = "Name field cannot be empty")
        @Size(min = 2, max = 20, message = "Name's length must be between 2 and 20 characters")
        String name,
        @NotBlank(message = "Surname field cannot be empty")
        @Size(min = 2, max = 20, message = "Surname's length must be between 2 and 20 characters")
        String surname,
        @Email(message = "E-mail format is not valid")
        @NotBlank(message = "Email field cannot be empty")
        String email,
        @NotBlank(message = "Password field cannot be empty")
        @Size(min = 8, max = 20, message = "Password's length must be between 8 and 20 characters")
        String password
) {
}
