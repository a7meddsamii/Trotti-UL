package ca.ulaval.glo4003.trotti.infrastructure.authentication;

import ca.ulaval.glo4003.trotti.domain.commons.Id;

public class TokenId extends Id {

    public static TokenId randomId() {
        return new TokenId();
    }

    private TokenId() {
        super();
    }

}
