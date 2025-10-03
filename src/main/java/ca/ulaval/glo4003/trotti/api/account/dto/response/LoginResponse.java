package ca.ulaval.glo4003.trotti.api.account.dto.response;

import ca.ulaval.glo4003.trotti.domain.authentication.AuthenticationToken;

public record LoginResponse(String token) {
    public LoginResponse(AuthenticationToken token) {
        this(token.toString());
    }
}
