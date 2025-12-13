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

import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RidePermitApplicationServiceTest {
    private static final Instant FIXED_INSTANT = Instant.parse("2025-01-01T10:00:00Z");
    private static final Idul VALID_RIDER_IDUL = Mockito.mock(Idul.class);
    private static final String VALID_RIDE_PERMIT_ID_VALUE = "123e4567-e89b-12d3-a456-426614174000";



    private RidePermitFactory ridePermitFactory;
    private RidePermitRepository ridePermitRepository;
    private PaymentGateway paymentGateway;
    private RidePermitAssembler ridePermitAssembler;
    private Clock clock;

    private RidePermitApplicationService ridePermitApplicationService;
    private RidePermitId ridePermitId;
    private LocalDate fixedDate;


    @BeforeEach
    void setup() {
        ridePermitFactory = Mockito.mock(RidePermitFactory.class);
        ridePermitRepository = Mockito.mock(RidePermitRepository.class);
        paymentGateway = Mockito.mock(PaymentGateway.class);
        ridePermitAssembler = Mockito.mock(RidePermitAssembler.class);
        clock = Clock.fixed(FIXED_INSTANT, ZoneOffset.UTC);
        ridePermitApplicationService = new RidePermitApplicationService(ridePermitFactory,
                ridePermitRepository, paymentGateway, ridePermitAssembler, clock);

        ridePermitId = RidePermitId.from(VALID_RIDE_PERMIT_ID_VALUE);
        fixedDate = LocalDate.ofInstant(FIXED_INSTANT, ZoneOffset.UTC);


    }

    @Test
    void givenRiderId_whenGetRidePermits_thenReturnPermitList() {
        RidePermit ridePermit = Mockito.mock(RidePermit.class);
        List<RidePermit> ridePermitList = List.of(ridePermit);
        RidePermitDto ridePermitDto = Mockito.mock(RidePermitDto.class);
        List<RidePermitDto> ridePermitDtoList = List.of(ridePermitDto);
        Mockito.when(ridePermitRepository.findAllByIdul(VALID_RIDER_IDUL)).thenReturn(ridePermitList);
        Mockito.when(ridePermitAssembler.assemble(ridePermitList)).thenReturn(ridePermitDtoList);

        List<RidePermitDto> result = ridePermitApplicationService.getRidePermits(VALID_RIDER_IDUL);

        Assertions.assertEquals(ridePermitDtoList, result);
    }

    @Test
    void givenValidIds_whenGetRidePermit_thenReturnsPermit() {
        RidePermit permit = Mockito.mock(RidePermit.class);
        RidePermitDto expectedDto = Mockito.mock(RidePermitDto.class);

        Mockito.when(ridePermitRepository.findByRiderIdAndRidePermitId(VALID_RIDER_IDUL, ridePermitId))
                .thenReturn(Optional.of(permit));
        Mockito.when(ridePermitAssembler.assemble(permit)).thenReturn(expectedDto);

        RidePermitDto result = ridePermitApplicationService.getRidePermit(VALID_RIDER_IDUL, ridePermitId);

        Assertions.assertEquals(expectedDto, result);
    }

    @Test
    void givenNonExistingPermit_whenGetRidePermit_thenThrowsNotFoundException() {
        Mockito.when(ridePermitRepository.findByRiderIdAndRidePermitId(VALID_RIDER_IDUL, ridePermitId))
                .thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(
                NotFoundException.class,
                () -> ridePermitApplicationService.getRidePermit(VALID_RIDER_IDUL, ridePermitId)
        );

        Assertions.assertNotNull(exception);
    }

    @Test
    void givenCreateRidePermitDtoList_whenCreateRidePermits_thenCreatesAndSavesAll() {
        CreateRidePermitDto createRidePermitDto = Mockito.mock(CreateRidePermitDto.class);
        List<CreateRidePermitDto> createRidePermitDtoList = List.of(createRidePermitDto);
        RidePermit ridePermit = Mockito.mock(RidePermit.class);
        Mockito.when(ridePermitFactory.create(Mockito.eq(VALID_RIDER_IDUL), Mockito.any(),
                Mockito.any(), Mockito.any())).thenReturn(ridePermit);

        ridePermitApplicationService.createRidePermits(VALID_RIDER_IDUL, createRidePermitDtoList);

        Mockito.verify(ridePermitFactory, Mockito.times(1)).create(Mockito.eq(VALID_RIDER_IDUL),
                Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void givenAddTravelTimeDto_whenAddTravelTime_thenCFindsByIdAddSDailyTravelTimeAndSaves() {
        AddTravelTimeDto addTimeDto = createAddTravelTimeDto();
        RidePermit permit = Mockito.mock(RidePermit.class);

        Mockito.when(ridePermitRepository.findById(ridePermitId)).thenReturn(Optional.of(permit));

        ridePermitApplicationService.addTravelTime(VALID_RIDER_IDUL, addTimeDto);

        Mockito.verify(permit).addDailyTravelTime(
                VALID_RIDER_IDUL,
                addTimeDto.startDateTime(),
                addTimeDto.travelTime()
        );
    }

    @Test
    void givenNonExistingPermit_whenAddTravelTime_thenThrowsNotFoundException() {
        AddTravelTimeDto addTimeDto = createAddTravelTimeDto();
        Mockito.when(ridePermitRepository.findById(ridePermitId)).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(
                NotFoundException.class,
                () -> ridePermitApplicationService.addTravelTime(VALID_RIDER_IDUL, addTimeDto)
        );

        Assertions.assertNotNull(exception);
    }

    @Test
    void givenActivePermit_whenIsRidePermitActive_thenReturnsTrue() {
        RidePermit permit = Mockito.mock(RidePermit.class);
        Mockito.when(ridePermitRepository.findById(ridePermitId)).thenReturn(Optional.of(permit));
        Mockito.when(permit.isActiveForRides(VALID_RIDER_IDUL, fixedDate)).thenReturn(true);

        boolean result = ridePermitApplicationService.isRidePermitActive(VALID_RIDER_IDUL, ridePermitId);

        Assertions.assertTrue(result);
    }

    @Test
    void givenInactivePermit_whenIsRidePermitActive_thenReturnsFalse() {
        RidePermit permit = Mockito.mock(RidePermit.class);
        Mockito.when(ridePermitRepository.findById(ridePermitId)).thenReturn(Optional.of(permit));
        Mockito.when(permit.isActiveForRides(VALID_RIDER_IDUL, fixedDate)).thenReturn(false);

        boolean result = ridePermitApplicationService.isRidePermitActive(VALID_RIDER_IDUL, ridePermitId);

        Assertions.assertFalse(result);
    }

    @Test
    void givenNonExistingPermit_whenIsRidePermitActive_thenThrowsNotFoundException() {
        Mockito.when(ridePermitRepository.findById(ridePermitId)).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(
                NotFoundException.class,
                () -> ridePermitApplicationService.isRidePermitActive(VALID_RIDER_IDUL, ridePermitId)
        );

        Assertions.assertNotNull(exception);
    }

    private AddTravelTimeDto createAddTravelTimeDto() {
        return new AddTravelTimeDto(
                ridePermitId,
                LocalDateTime.ofInstant(FIXED_INSTANT, ZoneOffset.UTC),
                Duration.ofMinutes(30)
        );
    }
}
