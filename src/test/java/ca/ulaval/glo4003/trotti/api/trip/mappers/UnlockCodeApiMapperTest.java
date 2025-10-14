package ca.ulaval.glo4003.trotti.api.trip.mappers;

import ca.ulaval.glo4003.trotti.api.trip.dto.UnlockCodeResponse;
import ca.ulaval.glo4003.trotti.application.trip.dto.UnlockCodeDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

class UnlockCodeApiMapperTest {

    private static final String CODE = "123456";
    private static final Duration TEN_SECONDS = Duration.ofSeconds(10);

    private UnlockCodeApiMapper mapper = new UnlockCodeApiMapper();

    @Test
    void whenToResponse_thenReturnsUnlockCodeResponse() {
        UnlockCodeDto unlockCodeDto = new UnlockCodeDto(CODE, TEN_SECONDS);

        UnlockCodeResponse response = mapper.toResponse(unlockCodeDto);

        Assertions.assertEquals(CODE, response.code());
        Assertions.assertEquals(TEN_SECONDS.getSeconds() + " seconds", response.expiresIn());
    }
}