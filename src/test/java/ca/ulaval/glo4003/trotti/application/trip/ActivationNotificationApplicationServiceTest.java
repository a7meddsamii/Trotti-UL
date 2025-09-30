package ca.ulaval.glo4003.trotti.application.trip;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.trip.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.Traveler;
import ca.ulaval.glo4003.trotti.domain.trip.repository.TravelerRepository;
import ca.ulaval.glo4003.trotti.domain.trip.services.RidePermitTranslator;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ActivationNotificationApplicationServiceTest {
    private static final int ANY_NUMBER_OF_TRAVELERS = 5;

    private TravelerRepository travelerRepository;
    private RidePermitTranslator ridePermitTranslator;
    private ActivationNotificationApplicationService activationNotificationApplicationService;

    @BeforeEach
    void setup() {
        travelerRepository = Mockito.mock(TravelerRepository.class);
        ridePermitTranslator = Mockito.mock(RidePermitTranslator.class);
        activationNotificationApplicationService =
                new ActivationNotificationApplicationService(travelerRepository, ridePermitTranslator);
    }

    @Test
    void givenTravelers_whenUpdateTravelersPermits_thenShouldFetchEachTravelersRidePermits() {
        List<Traveler> existingTravelers = mockTravelers(ANY_NUMBER_OF_TRAVELERS);
        Mockito.when(travelerRepository.findAll()).thenReturn(existingTravelers);

        activationNotificationApplicationService.updateTravelersPermits();

        Mockito.verify(ridePermitTranslator, Mockito.times(existingTravelers.size()))
                .findByIdul(Mockito.any(Idul.class));
    }

    @Test
    void givenTravelers_whenUpdateTravelersPermits_thenEachTravelersShouldUpdateActiveRidePermits() {
        List<Traveler> existingTravelers = mockTravelers(ANY_NUMBER_OF_TRAVELERS);
        Mockito.when(travelerRepository.findAll()).thenReturn(existingTravelers);

        activationNotificationApplicationService.updateTravelersPermits();

        existingTravelers.forEach(
                traveler -> Mockito.verify(traveler).updateActiveRidePermits(Mockito.anyList()));
    }

    @Test
    void givenTravelers_whenUpdateTravelersPermits_thenShouldSaveAllTravelers() {
        List<Traveler> existingTravelers = mockTravelers(ANY_NUMBER_OF_TRAVELERS);
        Mockito.when(travelerRepository.findAll()).thenReturn(existingTravelers);

        activationNotificationApplicationService.updateTravelersPermits();

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
            Mockito.when(ridePermitTranslator.findByIdul(idul))
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
