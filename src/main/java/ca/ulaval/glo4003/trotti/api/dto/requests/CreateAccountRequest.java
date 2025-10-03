package ca.ulaval.glo4003.trotti.api.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreateAccountRequest(
        @NotBlank(message = "Name is required") String name,
        @NotNull(message = "Birth date is required") LocalDate birthDate,
        @NotBlank(message = "Gender is required") String gender,
        @NotBlank(message = "IDUL is required") String idul,
        @NotBlank(message = "Email is required") String email,
        @NotBlank(message = "Password is required") String password
) {}