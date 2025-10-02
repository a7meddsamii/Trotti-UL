package ca.ulaval.glo4003.trotti.application.trip;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.trip.NotificationService;
import ca.ulaval.glo4003.trotti.domain.trip.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.Traveler;
import ca.ulaval.glo4003.trotti.domain.trip.repository.TravelerRepository;
import ca.ulaval.glo4003.trotti.domain.trip.services.RidePermitHistoryGateway;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RidePermitActivationApplicationServiceTest {
    private static final int A_NUMBER_OF_TRAVELERS = 5;

    private TravelerRepository travelerRepository;
    private RidePermitHistoryGateway ridePermitHistoryGateway;
    private RidePermitActivationApplicationService ridePermitActivationApplicationService;
    private NotificationService notificationService;
    private List<Traveler> existingTravelers;

    @BeforeEach
    void setup() {
        travelerRepository = Mockito.mock(TravelerRepository.class);
        ridePermitHistoryGateway = Mockito.mock(RidePermitHistoryGateway.class);
        notificationService = Mockito.mock(NotificationService.class);
        ridePermitActivationApplicationService = new RidePermitActivationApplicationService(
                travelerRepository, ridePermitHistoryGateway, notificationService);
        existingTravelers = mockTravelers();
        Mockito.when(travelerRepository.findAll()).thenReturn(existingTravelers);
    }

    @Test
    void givenTravelers_whenUpdateActivatedRidePermitsTravelersPermits_thenShouldFetchEach() {
        ridePermitActivationApplicationService.updateActivatedRidePermits();

        Mockito.verify(ridePermitHistoryGateway, Mockito.times(existingTravelers.size()))
                .getFullHistory(Mockito.any(Idul.class));
    }

    @Test
    void givenTravelers_whenUpdateTravelersPermits_thenEachTravelersShouldUpdateActivatedRidePermitActiveRidePermits() {
        ridePermitActivationApplicationService.updateActivatedRidePermits();

        existingTravelers.forEach(
                traveler -> Mockito.verify(traveler).updateActiveRidePermits(Mockito.anyList()));
    }

    @Test
    void givenTravelers_whenUpdateActivatedRidePermitsTravelersPermits_thenShouldSaveAllTravelers() {
        ridePermitActivationApplicationService.updateActivatedRidePermits();

        Mockito.verify(travelerRepository, Mockito.times(existingTravelers.size()))
                .update(Mockito.any(Traveler.class));
    }

    @Test
    void givenTravelersWithNewlyActivatedRidePermits_whenUpdateActivatedRidePermitsTravelersPermits_thenShouldNotifyEachTraveler() {
        List<Traveler> travelersWithoutNewActiveRidePermit =
                mockTravelersWithoutNewActivatedRidePermits();
        List<Traveler> travelersWithNewActiveRidePermit =
                mockTravelersWithNewActivatedRidePermits();
        List<Traveler> allTravelers = Stream.concat(travelersWithoutNewActiveRidePermit.stream(),
                travelersWithNewActiveRidePermit.stream()).toList();
        Mockito.when(travelerRepository.findAll()).thenReturn(allTravelers);

        ridePermitActivationApplicationService.updateActivatedRidePermits();

        Mockito.verify(notificationService, Mockito.times(travelersWithNewActiveRidePermit.size()))
                .notify(Mockito.any(Email.class), Mockito.anyList());
    }

    private List<Traveler> mockTravelersWithoutNewActivatedRidePermits() {
        List<Traveler> travelers = mockTravelers();
        travelers.forEach(traveler -> Mockito.when(traveler.updateActiveRidePermits(Mockito.any()))
                .thenReturn(Collections.emptyList()));

        return travelers;
    }

    private List<Traveler> mockTravelersWithNewActivatedRidePermits() {
        List<Traveler> travelers = mockTravelers();
        int numberOfRidePermits = RandomUtils.secure().randomInt(2, 20);
        List<RidePermit> newlyActivatedRidePermits = mockRidePermits(numberOfRidePermits);
        travelers.forEach(traveler -> Mockito.when(traveler.updateActiveRidePermits(Mockito.any()))
                .thenReturn(newlyActivatedRidePermits));

        return travelers;
    }

    private List<Traveler> mockTravelers() {
        List<Traveler> travelers = new java.util.ArrayList<>();

        for (int i = 0; i < A_NUMBER_OF_TRAVELERS; i++) {
            Idul idul = Idul.from(RandomStringUtils.secure().next(10));
            Traveler traveler = Mockito.mock(Traveler.class);
            int numberOfRidePermits = RandomUtils.secure().randomInt(0, 20);

            Mockito.lenient().when(traveler.getIdul()).thenReturn(idul);
            Mockito.lenient().when(traveler.getEmail()).thenReturn(Mockito.mock(Email.class));
            Mockito.lenient().when(ridePermitHistoryGateway.getFullHistory(idul))
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
