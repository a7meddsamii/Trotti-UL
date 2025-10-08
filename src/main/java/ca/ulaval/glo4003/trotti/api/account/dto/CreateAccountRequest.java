package ca.ulaval.glo4003.trotti.api.account.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateAccountRequest(
        @NotBlank(message = "Name is required") String name,
        @NotBlank(message = "Birth date is required") String  birthDate,
        @NotBlank(message = "Gender is required") String gender,
        @NotBlank(message = "IDUL is required") String idul,
        @NotBlank(message = "Email is required") String email,
        @NotBlank(message = "Password is required") String password
) {}
