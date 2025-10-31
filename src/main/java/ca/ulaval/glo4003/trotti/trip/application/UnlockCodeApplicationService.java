package ca.ulaval.glo4003.trotti.trip.application;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;
import ca.ulaval.glo4003.trotti.communication.domain.services.NotificationService;
import ca.ulaval.glo4003.trotti.trip.domain.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.trip.domain.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TravelerRepository;
import ca.ulaval.glo4003.trotti.trip.domain.services.UnlockCodeService;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;

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

        UnlockCode unlockCode = unlockCodeService.requestUnlockCode(traveler.getIdul());

        notificationService.notify(traveler.getEmail(), unlockCode);
    }
}
