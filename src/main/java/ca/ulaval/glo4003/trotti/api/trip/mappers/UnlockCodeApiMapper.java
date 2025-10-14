package ca.ulaval.glo4003.trotti.api.trip.mappers;

import ca.ulaval.glo4003.trotti.api.trip.dto.UnlockCodeResponse;
import ca.ulaval.glo4003.trotti.application.trip.dto.UnlockCodeDto;

public class UnlockCodeApiMapper {

    public UnlockCodeResponse toResponse(UnlockCodeDto code) {
        return new UnlockCodeResponse(code.code(), code.expirationTime().getSeconds() + " seconds");
    }
}

