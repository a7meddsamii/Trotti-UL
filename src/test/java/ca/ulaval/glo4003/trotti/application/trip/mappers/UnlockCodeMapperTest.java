package ca.ulaval.glo4003.trotti.application.trip.mappers;

import ca.ulaval.glo4003.trotti.application.trip.dto.UnlockCodeDto;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import java.time.Clock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UnlockCodeMapperTest {

    private static final Idul A_TRAVELER_ID = Idul.from("travelerId");
    private static final Clock A_CLOCK = Clock.systemUTC();

    private UnlockCodeMapper unlockCodeMapper ;

    @BeforeEach
    void setup() {
        unlockCodeMapper = new UnlockCodeMapper();
    }

    @Test
    void whenToDto_thenReturnsCorrectDto() {
        UnlockCode unlockCode = UnlockCode.generateFromTravelerId(A_TRAVELER_ID, A_CLOCK);

        UnlockCodeDto unlockCodeDto = unlockCodeMapper.toDto(unlockCode);

        Assertions.assertEquals(unlockCode.getCode(), unlockCodeDto.code());
        Assertions.assertEquals(unlockCode.getRemainingTime().getSeconds(),
                unlockCodeDto.expirationTime().getSeconds());
    }
}
