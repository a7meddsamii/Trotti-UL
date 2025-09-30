package ca.ulaval.glo4003.trotti.application.trip;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.trip.NotificationService;
import ca.ulaval.glo4003.trotti.domain.trip.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.Traveler;
import ca.ulaval.glo4003.trotti.domain.trip.repository.TravelerRepository;
import ca.ulaval.glo4003.trotti.domain.trip.services.RidePermitHistoryGateway;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RidePermitActivationApplicationServiceTest {
    private static final int ANY_NUMBER_OF_TRAVELERS = 5;

    private TravelerRepository travelerRepository;
    private RidePermitHistoryGateway ridePermitHistoryGateway;
    private RidePermitActivationApplicationService ridePermitActivationApplicationService;
    private NotificationService notificationService;

    @BeforeEach
    void setup() {
        travelerRepository = Mockito.mock(TravelerRepository.class);
        ridePermitHistoryGateway = Mockito.mock(RidePermitHistoryGateway.class);
        notificationService = Mockito.mock(NotificationService.class);
        ridePermitActivationApplicationService = new RidePermitActivationApplicationService(
                travelerRepository, ridePermitHistoryGateway, notificationService);
    }

    @Test
    void givenTravelers_whenUpdateTravelersPermits_thenShouldFetchEach() {
        List<Traveler> existingTravelers = mockTravelers(ANY_NUMBER_OF_TRAVELERS);
        Mockito.when(travelerRepository.findAll()).thenReturn(existingTravelers);

        ridePermitActivationApplicationService.update();

        Mockito.verify(ridePermitHistoryGateway, Mockito.times(existingTravelers.size()))
                .getFullHistory(Mockito.any(Idul.class));
    }

    @Test
    void givenTravelers_whenUpdateTravelersPermits_thenEachTravelersShouldUpdateActiveRidePermits() {
        List<Traveler> existingTravelers = mockTravelers(ANY_NUMBER_OF_TRAVELERS);
        Mockito.when(travelerRepository.findAll()).thenReturn(existingTravelers);

        ridePermitActivationApplicationService.update();

        existingTravelers.forEach(
                traveler -> Mockito.verify(traveler).updateActiveRidePermits(Mockito.anyList()));
    }

    @Test
    void givenTravelers_whenUpdateTravelersPermits_thenShouldSaveAllTravelers() {
        List<Traveler> existingTravelers = mockTravelers(ANY_NUMBER_OF_TRAVELERS);
        Mockito.when(travelerRepository.findAll()).thenReturn(existingTravelers);

        ridePermitActivationApplicationService.update();

        Mockito.verify(travelerRepository, Mockito.times(existingTravelers.size()))
                .update(Mockito.any(Traveler.class));
    }

    private List<Traveler> mockTravelers(int numberOfTravelers) {
        List<Traveler> travelers = new java.util.ArrayList<>();
        for (int i = 0; i < numberOfTravelers; i++) {
            Idul idul = Idul.from(RandomStringUtils.secure().next(10));
            Traveler traveler = Mockito.mock(Traveler.class);
            int numberOfRidePermits = RandomUtils.secure().randomInt(0, 20);

            Mockito.when(traveler.getIdul()).thenReturn(idul);
            Mockito.when(ridePermitHistoryGateway.getFullHistory(idul))
                    .thenReturn(mockRidePermits(numberOfRidePermits));
            travelers.add(traveler);
        }
        return travelers;
    }

    private List<RidePermit> mockRidePermits(int numberOfRidePermits) {
        List<RidePermit> ridePermits = new java.util.ArrayList<>();
        for (int i = 0; i < numberOfRidePermits; i++) {
            RidePermit ridePermit = Mockito.mock(RidePermit.class);
            ridePermits.add(ridePermit);
        }
        return ridePermits;
    }
}
