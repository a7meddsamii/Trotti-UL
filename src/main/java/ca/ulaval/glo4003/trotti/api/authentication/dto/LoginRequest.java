package ca.ulaval.glo4003.trotti.api.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "LoginRequest", description = "Un request body pour s'authentifier")
public record LoginRequest(
        @Schema(description = "Courriel Ulaval de l'utilisateur", example = "john.doe.1@ulaval.ca", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Email is required")
        String email,

        @Schema(description = "Mot de passe de l'utilisateur", example = "StrongP@ssw0rd!", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Password is required")
        String password
) {}
