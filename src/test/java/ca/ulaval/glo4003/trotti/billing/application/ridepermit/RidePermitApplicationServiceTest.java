package ca.ulaval.glo4003.trotti.billing.application.ridepermit;

import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.AddTravelTimeDto;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.CreateRidePermitDto;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.RidePermitDto;
import ca.ulaval.glo4003.trotti.billing.domain.payment.PaymentGateway;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities.RidePermit;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.factory.RidePermitFactory;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.repository.RidePermitRepository;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.*;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RidePermitApplicationServiceTest {
    private static final Instant FIXED_INSTANT = Instant.parse("2025-01-01T10:00:00Z");
    private static final Idul RIDER_IDUL = Mockito.mock(Idul.class);

    private RidePermitFactory ridePermitFactory;
    private RidePermitRepository ridePermitRepository;
    private PaymentGateway paymentGateway;
    private RidePermitAssembler ridePermitAssembler;
    private Clock clock;

    private RidePermitApplicationService ridePermitApplicationService;

    @BeforeEach
    void setup() {
        ridePermitFactory = Mockito.mock(RidePermitFactory.class);
        ridePermitRepository = Mockito.mock(RidePermitRepository.class);
        paymentGateway = Mockito.mock(PaymentGateway.class);
        ridePermitAssembler = Mockito.mock(RidePermitAssembler.class);
        clock = Clock.fixed(FIXED_INSTANT, ZoneOffset.UTC);
        this.ridePermitApplicationService = new RidePermitApplicationService(ridePermitFactory,
                ridePermitRepository, paymentGateway, ridePermitAssembler, clock);
    }

    @Test
    void givenRiderId_whenGetRidePermits_thenSaveFindsAllByIdulAndAssembles() {
        RidePermit ridePermit = Mockito.mock(RidePermit.class);
        List<RidePermit> ridePermitList = List.of(ridePermit);
        RidePermitDto ridePermitDto = Mockito.mock(RidePermitDto.class);
        List<RidePermitDto> ridePermitDtoList = List.of(ridePermitDto);
        Mockito.when(ridePermitRepository.findAllByIdul(RIDER_IDUL)).thenReturn(ridePermitList);
        Mockito.when(ridePermitAssembler.assemble(ridePermitList)).thenReturn(ridePermitDtoList);

        List<RidePermitDto> result = ridePermitApplicationService.getRidePermits(RIDER_IDUL);

        Mockito.verify(ridePermitRepository, Mockito.times(1)).findAllByIdul(RIDER_IDUL);
        Mockito.verify(ridePermitAssembler, Mockito.times(1)).assemble(ridePermitList);
        Assertions.assertEquals(ridePermitDtoList, result);
    }

    @Test
    void givenCreateRidePermitDtoList_whenCreateRidePermits_thenCreatesAndSavesAll() {
        CreateRidePermitDto createRidePermitDto = Mockito.mock(CreateRidePermitDto.class);
        List<CreateRidePermitDto> createRidePermitDtoList = List.of(createRidePermitDto);
        RidePermit ridePermit = Mockito.mock(RidePermit.class);
        Mockito.when(ridePermitFactory.create(Mockito.eq(RIDER_IDUL), Mockito.any(),
                Mockito.any(), Mockito.any())).thenReturn(ridePermit);

        ridePermitApplicationService.createRidePermits(RIDER_IDUL, createRidePermitDtoList);

        Mockito.verify(ridePermitFactory, Mockito.times(1)).create(Mockito.eq(RIDER_IDUL),
                Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.verify(ridePermitRepository, Mockito.times(1)).saveAll(Mockito.anyList());
    }

    @Test
    void givenAddTravelTimeDto_whenAddTravelTime_thenCFindsByIdAddSDailyTravelTimeAndSaves() {
        AddTravelTimeDto addTravelTimeDto = Mockito.mock(AddTravelTimeDto.class);
        RidePermitId permitId = RidePermitId.randomId();
        Mockito.when(addTravelTimeDto.ridePermitId()).thenReturn(permitId);
        Mockito.when(addTravelTimeDto.startDateTime()).thenReturn(LocalDateTime.now());
        Mockito.when(addTravelTimeDto.travelTime()).thenReturn(Duration.ofMinutes(10));
        RidePermit ridePermit = Mockito.mock(RidePermit.class);
        Mockito.when(ridePermitRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(ridePermit));

        ridePermitApplicationService.addTravelTime(RIDER_IDUL, addTravelTimeDto);

        Mockito.verify(ridePermitRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(ridePermit, Mockito.times(1)).addDailyTravelTime(Mockito.eq(RIDER_IDUL),
                Mockito.any(), Mockito.any());
        Mockito.verify(ridePermitRepository, Mockito.times(1)).save(ridePermit);
    }
}
