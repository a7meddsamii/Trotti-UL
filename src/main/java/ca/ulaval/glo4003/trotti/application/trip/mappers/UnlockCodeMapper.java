package ca.ulaval.glo4003.trotti.application.trip.mappers;

import ca.ulaval.glo4003.trotti.application.trip.dto.UnlockCodeDto;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;

public class UnlockCodeMapper {

    public UnlockCodeDto toDto(UnlockCode unlockCode) {
        return new UnlockCodeDto(unlockCode.getCode(), unlockCode.getRemainingTime());
    }

}
