package ca.ulaval.glo4003.trotti.api.authentication.dto;

import ca.ulaval.glo4003.trotti.domain.authentication.values.AuthenticationToken;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response retourné lors d'une tentative de connexion réussie.")
public record LoginResponse(
        @Schema(description = "Jeton d'authentification à utiliser pour les requêtes authentifiées.", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huLmRvZUBleGFtcGxlLmNvbSIsImlhdCI6MTYxNjIzOTAyMn0.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
        String token) {

    public LoginResponse(AuthenticationToken token) {
        this(token.toString());
    }
}
