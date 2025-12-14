package ca.ulaval.glo4003.trotti.billing.application.ridepermit;

import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.AddTravelTimeDto;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.CreateRidePermitDto;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.RidePermitDto;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.Semester;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.billing.domain.payment.PaymentGateway;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities.RidePermit;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.factory.RidePermitFactory;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.repository.RidePermitRepository;
import ca.ulaval.glo4003.trotti.billing.infrastructure.ridepermit.repository.InMemoryRidePermitRepository;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.*;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RidePermitApplicationServiceIntegrationTest {
    private static final Instant FIXED_INSTANT = Instant.parse("2025-01-01T10:00:00Z");
    private static final Idul RIDER_IDUL = Idul.from("IDUL123");
    private static final Session TEST_SESSION = new Session(Semester.FALL, LocalDate.of(2025, 9, 1), LocalDate.of(2025, 12, 20));

    private RidePermitFactory ridePermitFactory;
    private RidePermitRepository ridePermitRepository;
    private PaymentGateway paymentGateway;
    private RidePermitAssembler ridePermitAssembler;
    private Clock clock;
    private CreateRidePermitDto createRidePermitDto;

    private RidePermitApplicationService ridePermitApplicationService;

    @BeforeEach
    void setup() {
        ridePermitFactory = new RidePermitFactory();
        ridePermitRepository = new InMemoryRidePermitRepository();
        ridePermitAssembler = new RidePermitAssembler();
        clock = Clock.fixed(FIXED_INSTANT, ZoneOffset.UTC);
        
        paymentGateway = Mockito.mock(PaymentGateway.class);
        
        createRidePermitDto = new CreateRidePermitDto(TEST_SESSION, Duration.ofHours(2), BillingFrequency.MONTHLY);
        
        this.ridePermitApplicationService = new RidePermitApplicationService(ridePermitFactory,
                ridePermitRepository, paymentGateway, ridePermitAssembler, clock);
    }

    @Test
    void givenRiderId_whenGetRidePermits_thenReturnsRidePermitDtos() {
        RidePermit ridePermit = ridePermitFactory.create(RIDER_IDUL, TEST_SESSION, Duration.ofHours(2), BillingFrequency.MONTHLY);
        ridePermitRepository.save(ridePermit);

        List<RidePermitDto> result = ridePermitApplicationService.getRidePermits(RIDER_IDUL);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(RIDER_IDUL, result.get(0).riderId());
        Assertions.assertEquals(TEST_SESSION, result.get(0).session());
    }

    @Test
    void givenCreateRidePermitDtoList_whenCreateRidePermits_thenCreatesAndSavesAll() {
        List<CreateRidePermitDto> createRidePermitDtoList = List.of(createRidePermitDto);

        ridePermitApplicationService.createRidePermits(RIDER_IDUL, createRidePermitDtoList);

        List<RidePermit> savedPermits = ridePermitRepository.findAllByIdul(RIDER_IDUL);
        Assertions.assertEquals(1, savedPermits.size());
        Assertions.assertEquals(RIDER_IDUL, savedPermits.get(0).getRiderId());
        Assertions.assertEquals(TEST_SESSION, savedPermits.get(0).getSession());
    }

    @Test
    void givenAddTravelTimeDto_whenAddTravelTime_thenAddsTravelTimeToRidePermit() {
        RidePermit ridePermit = ridePermitFactory.create(RIDER_IDUL, TEST_SESSION, Duration.ofHours(2), BillingFrequency.MONTHLY);
        ridePermitRepository.save(ridePermit);
        LocalDateTime startDateTime = LocalDateTime.of(2025, 9, 15, 10, 0);
        Duration travelTime = Duration.ofMinutes(30);
        AddTravelTimeDto addTravelTimeDto = new AddTravelTimeDto(ridePermit.getId(), startDateTime, travelTime);

        ridePermitApplicationService.addTravelTime(RIDER_IDUL, addTravelTimeDto);

        Optional<RidePermit> updatedPermit = ridePermitRepository.findById(ridePermit.getId());
        Assertions.assertTrue(updatedPermit.isPresent());
        Assertions.assertFalse(updatedPermit.get().getDailyBillingUsages().isEmpty());
    }

    @Test
    void givenNoRidePermits_whenGetRidePermits_thenReturnsEmptyList() {
        List<RidePermitDto> result = ridePermitApplicationService.getRidePermits(RIDER_IDUL);

        Assertions.assertTrue(result.isEmpty());
    }
}
