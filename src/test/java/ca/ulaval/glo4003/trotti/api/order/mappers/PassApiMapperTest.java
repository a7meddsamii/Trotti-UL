package ca.ulaval.glo4003.trotti.api.order.mappers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.trotti.api.order.dto.requests.PassListRequest;
import ca.ulaval.glo4003.trotti.api.order.dto.responses.PassListResponse;
import ca.ulaval.glo4003.trotti.application.order.dto.PassDto;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.domain.order.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.domain.order.values.MaximumDailyTravelTime;
import ca.ulaval.glo4003.trotti.domain.order.values.Semester;
import ca.ulaval.glo4003.trotti.domain.payment.values.money.Money;
import ca.ulaval.glo4003.trotti.infrastructure.config.providers.SessionProvider;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class PassApiMapperTest {
    private static final Session SESSION = new Session(Semester.FALL,
            LocalDate.of(2025, Month.SEPTEMBER, 2), LocalDate.of(2025, Month.DECEMBER, 12));
    private PassApiMapper mapper;

    @BeforeEach
    void setup() {
        SessionProvider sessionProviderMock = mock(SessionProvider.class);
        when(sessionProviderMock.getSessions()).thenReturn(new ArrayList<>(List.of(SESSION)));

        mapper = new PassApiMapper(sessionProviderMock);
    }

    @Test
    void givenValidPassListRequest_whenToPassDtoList_thenMapsCorrectly() {
        PassListRequest request =
                new PassListRequest(List.of(new PassListRequest.PassRequest(60, "A25", "MONTHLY")));

        List<PassDto> dtoList = mapper.toPassDtoList(request);

        Assertions.assertEquals(MaximumDailyTravelTime.from(Duration.ofMinutes(60)).toString(),
                dtoList.getFirst().maximumDailyTravelTime().toString());
        Assertions.assertEquals(SESSION.toString(), dtoList.getFirst().session().toString());
        Assertions.assertEquals(BillingFrequency.MONTHLY, dtoList.getFirst().billingFrequency());
    }

    @Test
    void givenNullPassListRequest_whenToPassDtoList_thenThrowsException() {
        PassListRequest request = null;

        Executable executable = () -> mapper.toPassDtoList(request);

        Assertions.assertThrows(InvalidParameterException.class, executable);
    }

    @Test
    void givenPassDtoList_whenToPassListResponse_thenMapsCorrectly() {
        MaximumDailyTravelTime maxTime = MaximumDailyTravelTime.from(Duration.ofMinutes(60));
        PassDto passDto = new PassDto(maxTime, SESSION, BillingFrequency.MONTHLY, Id.randomId());

        PassListResponse response = mapper.toPassListResponse(List.of(passDto));

        Assertions.assertEquals(1, response.passes().size());
        Assertions.assertEquals(maxTime.calculateAmount().toString(), response.total());
    }

    @Test
    void givenMultiplePasses_whenToPassListResponse_thenAggregatesTotalCorrectly() {
        MaximumDailyTravelTime maxTime1 = MaximumDailyTravelTime.from(Duration.ofMinutes(60));
        MaximumDailyTravelTime maxTime2 = MaximumDailyTravelTime.from(Duration.ofMinutes(120));
        PassDto passDto1 = new PassDto(maxTime1, SESSION, BillingFrequency.MONTHLY, Id.randomId());
        PassDto passDto2 = new PassDto(maxTime2, SESSION, BillingFrequency.MONTHLY, Id.randomId());

        PassListResponse response = mapper.toPassListResponse(List.of(passDto1, passDto2));

        Money expectedTotal = maxTime1.calculateAmount().plus(maxTime2.calculateAmount());
        Assertions.assertEquals(expectedTotal.toString(), response.total());
    }

    @Test
    void givenEmptySessionProviderSessions_whenToPassListResponse_thenMapsCorrectly() {
        SessionProvider sessionProviderMock = mock(SessionProvider.class);
        when(sessionProviderMock.getSessions()).thenReturn(new ArrayList<>());
        mapper = new PassApiMapper(sessionProviderMock);
        PassListRequest request =
                new PassListRequest(List.of(new PassListRequest.PassRequest(60, "A25", "MONTHLY")));

        Executable executable = () -> mapper.toPassDtoList(request);

        Assertions.assertThrows(InvalidParameterException.class, executable);
    }
}
