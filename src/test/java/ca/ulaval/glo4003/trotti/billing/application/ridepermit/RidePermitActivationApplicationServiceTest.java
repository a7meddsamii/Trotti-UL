package ca.ulaval.glo4003.trotti.billing.application.ridepermit;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities.RidePermit;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.repository.RidePermitRepository;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.service.RidePermitActivationFilter;
import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.ridepermit.RidePermitActivatedEvent;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.ridepermit.RidePermitSnapshot;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class RidePermitActivationApplicationServiceTest {

    private RidePermitAssembler ridePermitAssembler;
    private RidePermitRepository ridePermitRepository;
    private RidePermitActivationFilter ridePermitActivationFilter;
    private EventBus eventBus;

    public RidePermitActivationApplicationService ridePermitActivationApplicationService;

    @BeforeEach
    void setUp() {
        ridePermitAssembler = Mockito.mock(RidePermitAssembler.class);
        ridePermitRepository = Mockito.mock(RidePermitRepository.class);
        ridePermitActivationFilter = Mockito.mock(RidePermitActivationFilter.class);
        eventBus = Mockito.mock(EventBus.class);
        ridePermitActivationApplicationService = new RidePermitActivationApplicationService(
                ridePermitAssembler, ridePermitRepository, ridePermitActivationFilter, eventBus);
    }

    @Test
    void givenRidePermitsToActivate_whenActivateRidePermit_thenPermitsActivatedAndEventPublished() {
        LocalDate currentDate = LocalDate.of(2026, 1, 15);
        RidePermit permit1 = Mockito.mock(RidePermit.class);
        RidePermit permit2 = Mockito.mock(RidePermit.class);
        List<RidePermit> foundPermits = List.of(permit1, permit2);
        List<RidePermit> activatedPermits = List.of(permit1, permit2);
        List<RidePermitSnapshot> snapshots = List.of(Mockito.mock(RidePermitSnapshot.class),
                Mockito.mock(RidePermitSnapshot.class));

        Mockito.when(ridePermitActivationFilter.getCurrentSessionDate()).thenReturn(currentDate);
        Mockito.when(ridePermitRepository.findAllByDate(currentDate)).thenReturn(foundPermits);
        Mockito.when(ridePermitActivationFilter.getActivatedRidePermits(foundPermits))
                .thenReturn(activatedPermits);
        Mockito.when(ridePermitAssembler.toRidePermitSnapshots(activatedPermits))
                .thenReturn(snapshots);

        ridePermitActivationApplicationService.activateRidePermit();

        Mockito.verify(ridePermitRepository).saveAll(activatedPermits);

        ArgumentCaptor<RidePermitActivatedEvent> eventCaptor =
                ArgumentCaptor.forClass(RidePermitActivatedEvent.class);
        Mockito.verify(eventBus).publish(eventCaptor.capture());

        RidePermitActivatedEvent event = eventCaptor.getValue();
        Assertions.assertEquals(snapshots, event.getRidePermitSnapshot());
    }

    @Test
    void givenNoRidePermitsToActivate_whenActivateRidePermit_thenNoEventPublished() {
        LocalDate currentDate = LocalDate.of(2026, 1, 15);
        List<RidePermit> foundPermits = List.of();
        List<RidePermit> activatedPermits = List.of();

        Mockito.when(ridePermitActivationFilter.getCurrentSessionDate()).thenReturn(currentDate);
        Mockito.when(ridePermitRepository.findAllByDate(currentDate)).thenReturn(foundPermits);
        Mockito.when(ridePermitActivationFilter.getActivatedRidePermits(foundPermits))
                .thenReturn(activatedPermits);

        ridePermitActivationApplicationService.activateRidePermit();

        Mockito.verify(ridePermitRepository).saveAll(activatedPermits);
        Mockito.verify(eventBus, Mockito.never()).publish(Mockito.any());
    }

    @Test
    void givenRidePermitsFoundButNoneToActivate_whenActivateRidePermit_thenNoEventPublished() {
        LocalDate currentDate = LocalDate.of(2026, 1, 15);
        RidePermit permit1 = Mockito.mock(RidePermit.class);
        List<RidePermit> foundPermits = List.of(permit1);
        List<RidePermit> activatedPermits = List.of();

        Mockito.when(ridePermitActivationFilter.getCurrentSessionDate()).thenReturn(currentDate);
        Mockito.when(ridePermitRepository.findAllByDate(currentDate)).thenReturn(foundPermits);
        Mockito.when(ridePermitActivationFilter.getActivatedRidePermits(foundPermits))
                .thenReturn(activatedPermits);

        ridePermitActivationApplicationService.activateRidePermit();

        Mockito.verify(ridePermitRepository).saveAll(activatedPermits);
        Mockito.verify(eventBus, Mockito.never()).publish(Mockito.any());
    }

    @Test
    void givenRidePermitsToDeactivate_whenDeactivateRidePermit_thenPermitsDeactivated() {
        Session previousSession = Mockito.mock(Session.class);
        RidePermit permit1 = Mockito.mock(RidePermit.class);
        RidePermit permit2 = Mockito.mock(RidePermit.class);
        List<RidePermit> foundPermits = List.of(permit1, permit2);
        List<RidePermit> deactivatedPermits = List.of(permit1, permit2);

        Mockito.when(ridePermitActivationFilter.getPreviousSession()).thenReturn(previousSession);
        Mockito.when(ridePermitRepository.findAllBySession(previousSession))
                .thenReturn(foundPermits);
        Mockito.when(ridePermitActivationFilter.getDeactivatedRidePermits(foundPermits))
                .thenReturn(deactivatedPermits);

        ridePermitActivationApplicationService.deactivateRidePermit();

        Mockito.verify(ridePermitRepository).saveAll(deactivatedPermits);
    }

    @Test
    void givenNoRidePermitsToDeactivate_whenDeactivateRidePermit_thenSavesEmptyList() {
        Session previousSession = Mockito.mock(Session.class);
        List<RidePermit> foundPermits = List.of();
        List<RidePermit> deactivatedPermits = List.of();

        Mockito.when(ridePermitActivationFilter.getPreviousSession()).thenReturn(previousSession);
        Mockito.when(ridePermitRepository.findAllBySession(previousSession))
                .thenReturn(foundPermits);
        Mockito.when(ridePermitActivationFilter.getDeactivatedRidePermits(foundPermits))
                .thenReturn(deactivatedPermits);

        ridePermitActivationApplicationService.deactivateRidePermit();

        Mockito.verify(ridePermitRepository).saveAll(deactivatedPermits);
    }
}
