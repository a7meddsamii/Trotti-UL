package ca.ulaval.glo4003.trotti.billing.application.ridepermit;

import ca.ulaval.glo4003.trotti.billing.domain.order.provider.SchoolSessionProvider;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.Semester;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities.RidePermit;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.repository.RidePermitRepository;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.service.RidePermitActivationFilter;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitState;
import ca.ulaval.glo4003.trotti.billing.infrastructure.ridepermit.repository.InMemoryRidePermitRepository;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.ridepermit.RidePermitActivatedEvent;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RidePermitActivationApplicationServiceIntegrationTest {

    private static final Idul RIDER_ID = Idul.from("IDUL123");
    private static final LocalDate CURRENT_DATE = LocalDate.of(2025, 9, 15);
    private static final Session CURRENT_SESSION =
            new Session(Semester.FALL, LocalDate.of(2025, 9, 1), LocalDate.of(2025, 12, 20));
    private static final Session PREVIOUS_SESSION =
            new Session(Semester.SUMMER, LocalDate.of(2025, 5, 1), LocalDate.of(2025, 8, 31));

    private RidePermitAssembler ridePermitAssembler;
    private RidePermitRepository ridePermitRepository;
    private RidePermitActivationFilter ridePermitActivationFilter;
    private EventBus eventBus;
    private SchoolSessionProvider schoolSessionProvider;
    private Clock clock;

    private RidePermitActivationApplicationService ridePermitActivationApplicationService;

    @BeforeEach
    void setUp() {
        ridePermitAssembler = new RidePermitAssembler();
        ridePermitRepository = new InMemoryRidePermitRepository();
        eventBus = Mockito.mock(EventBus.class);

        schoolSessionProvider = Mockito.mock(SchoolSessionProvider.class);
        clock = Mockito.mock(Clock.class);

        setupMocks();

        ridePermitActivationFilter = new RidePermitActivationFilter(clock, schoolSessionProvider);
        ridePermitActivationApplicationService = new RidePermitActivationApplicationService(
                ridePermitAssembler, ridePermitRepository, ridePermitActivationFilter, eventBus);
    }

    @Test
    void givenInactiveRidePermitInCurrentSession_whenActivateRidePermit_thenRidePermitIsActivated() {
        RidePermit inactivePermit = createInactiveRidePermit(CURRENT_SESSION);
        ridePermitRepository.save(inactivePermit);

        ridePermitActivationApplicationService.activateRidePermit();

        Optional<RidePermit> updatedPermit = ridePermitRepository.findById(inactivePermit.getId());
        Mockito.verify(eventBus).publish(Mockito.any(RidePermitActivatedEvent.class));
        Assertions.assertTrue(updatedPermit.isPresent());
        Assertions.assertEquals(RidePermitState.ACTIVE, updatedPermit.get().getPermitState());
    }

    @Test
    void givenNoRidePermitsForCurrentDate_whenActivateRidePermit_thenNoPermitsActivated() {
        ridePermitActivationApplicationService.activateRidePermit();

        List<RidePermit> allPermits = ridePermitRepository.findAllByDate(CURRENT_DATE);
        Assertions.assertTrue(allPermits.isEmpty());
    }

    @Test
    void givenActiveRidePermitInPreviousSession_whenDeactivateRidePermit_thenRidePermitIsDeactivated() {
        RidePermit activePermit = createActiveRidePermit(PREVIOUS_SESSION);
        ridePermitRepository.save(activePermit);

        ridePermitActivationApplicationService.deactivateRidePermit();

        Optional<RidePermit> updatedPermit = ridePermitRepository.findById(activePermit.getId());
        Assertions.assertTrue(updatedPermit.isPresent());
        Assertions.assertEquals(RidePermitState.EXPIRED, updatedPermit.get().getPermitState());
    }

    @Test
    void givenMultipleInactiveRidePermits_whenActivateRidePermit_thenOnlyCurrentSessionPermitsActivated() {
        RidePermit currentSessionPermit = createInactiveRidePermit(CURRENT_SESSION);
        RidePermit otherSessionPermit = createInactiveRidePermit(PREVIOUS_SESSION);
        ridePermitRepository.save(currentSessionPermit);
        ridePermitRepository.save(otherSessionPermit);

        ridePermitActivationApplicationService.activateRidePermit();

        Optional<RidePermit> currentPermit =
                ridePermitRepository.findById(currentSessionPermit.getId());
        Optional<RidePermit> otherPermit =
                ridePermitRepository.findById(otherSessionPermit.getId());
        Assertions.assertTrue(currentPermit.isPresent());
        Assertions.assertEquals(RidePermitState.ACTIVE, currentPermit.get().getPermitState());
        Assertions.assertTrue(otherPermit.isPresent());
        Assertions.assertEquals(RidePermitState.INACTIVE, otherPermit.get().getPermitState());
    }

    private void setupMocks() {
        Mockito.when(clock.instant()).thenReturn(CURRENT_DATE.atStartOfDay()
                .atZone(Clock.systemDefaultZone().getZone()).toInstant());
        Mockito.when(clock.getZone()).thenReturn(Clock.systemDefaultZone().getZone());
        Mockito.when(schoolSessionProvider.getSession(CURRENT_DATE))
                .thenReturn(Optional.of(CURRENT_SESSION));
        Mockito.when(schoolSessionProvider.getPreviousSession(CURRENT_DATE))
                .thenReturn(Optional.of(PREVIOUS_SESSION));
    }

    private RidePermit createInactiveRidePermit(Session session) {
        return new RidePermit(RidePermitId.randomId(), RIDER_ID, session, Duration.ofHours(2));
    }

    private RidePermit createActiveRidePermit(Session session) {
        RidePermit permit =
                new RidePermit(RidePermitId.randomId(), RIDER_ID, session, Duration.ofHours(2));
        permit.activate(session);
        return permit;
    }
}
