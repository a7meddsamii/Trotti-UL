package ca.ulaval.glo4003.trotti.billing.application.ridepermit;

import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.repository.RidePermitRepository;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.service.RidePermitActivationFilter;
import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import org.junit.jupiter.api.BeforeEach;
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

    /*
     * @Test void givenCurrentSession_whenActivateRidePermit_thenOrchestratesProperly() { RidePermit
     * ridePermit = Mockito.mock(RidePermit.class); List<RidePermit> ridePermitList =
     * List.of(ridePermit);
     * Mockito.when(ridePermitRepository.findAllByDate(Mockito.any(LocalDate.class))).thenReturn(
     * ridePermitList);
     * Mockito.when(ridePermitActivationFilter.getActivatedRidePermits(ridePermitList)).thenReturn(
     * ridePermitList);
     * 
     * 
     * ridePermitActivationApplicationService.activateRidePermit();
     * 
     * Mockito.verify(ridePermitRepository,
     * Mockito.times(1)).findAllByDate(Mockito.any(LocalDate.class));
     * Mockito.verify(ridePermitActivationFilter,
     * Mockito.times(1)).getActivatedRidePermits(Mockito.anyList());
     * Mockito.verify(ridePermitRepository, Mockito.times(1)).saveAll(Mockito.anyList());
     * 
     * }
     * 
     * @Test void givenA_whenDeactivateRidePermit_thenC() {
     * 
     * }
     */
}
