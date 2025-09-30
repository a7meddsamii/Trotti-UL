package ca.ulaval.glo4003.trotti.application.trip;

import ca.ulaval.glo4003.trotti.domain.trip.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.Traveler;
import ca.ulaval.glo4003.trotti.domain.trip.repository.TravelerRepository;
import ca.ulaval.glo4003.trotti.domain.trip.services.RidePermitTranslator;
import java.util.List;

public class ActivationNotificationService {
    private final TravelerRepository travelerRepository;
    private final RidePermitTranslator ridePermitTranslator;

    public ActivationNotificationService(
            TravelerRepository travelerRepository,
            RidePermitTranslator ridePermitTranslator) {

        this.travelerRepository = travelerRepository;
        this.ridePermitTranslator = ridePermitTranslator;
    }

    public void updateTravelersPermits() {
        List<Traveler> travelers = travelerRepository.findAll();

        travelers.forEach(traveler -> {
            List<RidePermit> boughtRidePermits =
                    ridePermitTranslator.findByIdul(traveler.getIdul());
            traveler.updateActiveRidePermits(boughtRidePermits);
            travelerRepository.update(traveler);
        });
    }

}
