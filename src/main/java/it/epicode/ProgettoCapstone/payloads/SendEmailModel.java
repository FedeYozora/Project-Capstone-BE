package it.epicode.ProgettoCapstone.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SendEmailModel(
        @NotBlank(message = "Name field cannot be empty")
        String contactName,
        @Email(message = "E-mail format is not valid")
        @NotBlank(message = "Mail field cannot be empty")
        String email,
        @NotBlank(message = "Phone field cannot be empty")
        String phone,
        @NotBlank(message = "Message field cannot be empty")
        String message) {
}
