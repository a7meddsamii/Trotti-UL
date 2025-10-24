package ca.ulaval.glo4003.trotti.application.trip.mappers;

import ca.ulaval.glo4003.trotti.application.trip.dto.UnlockCodeDto;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.domain.trip.values.RidePermitId;
import java.time.Clock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UnlockCodeMapperTest {

    private static final RidePermitId A_RIDE_PERMIT_ID = RidePermitId.randomId();
    private static final Clock A_CLOCK = Clock.systemUTC();

    private UnlockCodeMapper unlockCodeMapper = new UnlockCodeMapper();

    @Test
    void whenToDto_thenReturnsCorrectDto() {
        UnlockCode unlockCode = UnlockCode.generateFromRidePermit(A_RIDE_PERMIT_ID, A_CLOCK);

        UnlockCodeDto unlockCodeDto = unlockCodeMapper.toDto(unlockCode);

        Assertions.assertEquals(unlockCode.getCode(), unlockCodeDto.code());
        Assertions.assertEquals(unlockCode.getRemainingTime().getSeconds(),
                unlockCodeDto.expirationTime().getSeconds());
    }
}
