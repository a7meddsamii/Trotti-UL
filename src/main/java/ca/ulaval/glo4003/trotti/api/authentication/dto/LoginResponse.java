package ca.ulaval.glo4003.trotti.api.authentication.dto;

import ca.ulaval.glo4003.trotti.domain.authentication.values.AuthenticationToken;

public record LoginResponse(String token) {
    public LoginResponse(AuthenticationToken token) {
        this(token.toString());
    }
}
