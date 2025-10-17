package ca.ulaval.glo4003.trotti.application.trip;

import ca.ulaval.glo4003.trotti.application.trip.dto.UnlockCodeDto;
import ca.ulaval.glo4003.trotti.application.trip.mappers.UnlockCodeMapper;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.NotFoundException;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Traveler;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.TravelerRepository;
import ca.ulaval.glo4003.trotti.domain.trip.services.UnlockCodeService;

public class UnlockCodeApplicationService {

    private final UnlockCodeService unlockCodeService;
    private final TravelerRepository travelerRepository;
    private final UnlockCodeMapper unlockCodeMapper;

    public UnlockCodeApplicationService(
            UnlockCodeService unlockCodeService,
            TravelerRepository travelerRepository,
            UnlockCodeMapper unlockCodeMapper) {
        this.unlockCodeService = unlockCodeService;
        this.travelerRepository = travelerRepository;
        this.unlockCodeMapper = unlockCodeMapper;
    }

    public UnlockCodeDto generateUnlockCode(Idul idul, Id ridePermitId) {
        Traveler traveler = travelerRepository.findByIdul(idul);

        if (!traveler.hasRidePermit(ridePermitId)) {
            throw new NotFoundException("Ride permit not found or not activated for this traveler");
        }

        UnlockCode unlockCode = unlockCodeService.requestUnlockCode(ridePermitId);

        return unlockCodeMapper.toDto(unlockCode);
    }
}
