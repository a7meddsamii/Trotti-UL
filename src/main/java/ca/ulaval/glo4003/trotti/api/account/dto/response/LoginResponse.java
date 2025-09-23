package ca.ulaval.glo4003.trotti.api.account.dto.response;

import ca.ulaval.glo4003.trotti.domain.account.authentication.AuthenticationToken;

public record LoginResponse (AuthenticationToken token) {}