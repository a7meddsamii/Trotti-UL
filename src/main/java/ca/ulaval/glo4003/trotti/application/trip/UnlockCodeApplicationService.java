package ca.ulaval.glo4003.trotti.application.trip;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.communication.services.NotificationService;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.NotFoundException;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.domain.trip.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.TravelerRepository;
import ca.ulaval.glo4003.trotti.domain.trip.services.UnlockCodeService;
import ca.ulaval.glo4003.trotti.domain.trip.values.RidePermitId;

public class UnlockCodeApplicationService {

    private final UnlockCodeService unlockCodeService;
    private final TravelerRepository travelerRepository;
    private final NotificationService<UnlockCode> notificationService;

    public UnlockCodeApplicationService(
            UnlockCodeService unlockCodeService,
            TravelerRepository travelerRepository,
            NotificationService<UnlockCode> notificationService) {
        this.unlockCodeService = unlockCodeService;
        this.travelerRepository = travelerRepository;
        this.notificationService = notificationService;
    }

    public void generateUnlockCode(Idul idul, RidePermitId ridePermitId) {
        Traveler traveler = travelerRepository.findByIdul(idul);

        if (!traveler.walletHasPermit(ridePermitId)) {
            throw new NotFoundException("Ride permit not found or not activated for this traveler");
        }

        UnlockCode unlockCode = unlockCodeService.requestUnlockCode(ridePermitId);

        notificationService.notify(traveler.getEmail(), unlockCode);
    }
}
