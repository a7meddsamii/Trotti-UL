package ca.ulaval.glo4003.trotti.account.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "CreateAccountRequest", description = "Un request body pour créer un nouveau compte")
public record CreateAccountRequest(
        @NotBlank(message = "Name is required")
        @Schema(description = "Nom complet de l'utilisateur", example = "Jean Dupont", requiredMode = Schema.RequiredMode.REQUIRED)
        String name,

        @NotBlank(message = "Birth date is required")
        @Schema(description = "Date de naissance au format ISO (YYYY-MM-DD)", example = "1990-01-01", format = "date", requiredMode = Schema.RequiredMode.REQUIRED)
        String birthDate,

        @NotBlank(message = "Gender is required")
        @Schema(description = "Genre de l'utilisateur", example = "MALE", requiredMode = Schema.RequiredMode.REQUIRED)
        String gender,

        @NotBlank(message = "IDUL is required")
        @Schema(description = "Identifiant Ulaval (IDUL)", example = "user123", requiredMode = Schema.RequiredMode.REQUIRED)
        String idul,

        @NotBlank(message = "Email is required")
        @Schema(description = "Adresse courriel de l'utilisateur", example = "utilisateur@ulaval.ca", format = "email", requiredMode = Schema.RequiredMode.REQUIRED)
        String email,

        @NotBlank(message = "Password is required")
        @Schema(description = "Mot de passe de l'utilisateur", example = "Cec1EstUnM0tDePasseF0rt!", format = "password", requiredMode = Schema.RequiredMode.REQUIRED)
        String password,

        @NotBlank(message = "Role is required")
        @Schema(description = "Role de l'utilisateur", example = "STUDENT", requiredMode = Schema.RequiredMode.REQUIRED)
        String role


) {}
