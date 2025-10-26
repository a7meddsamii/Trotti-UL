package ca.ulaval.glo4003.trotti.application.trip;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.values.SlotNumber;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Scooter;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Trip;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.domain.trip.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.TravelerRepository;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.TripRepository;
import ca.ulaval.glo4003.trotti.domain.trip.services.UnlockCodeService;
import ca.ulaval.glo4003.trotti.domain.trip.store.UnlockCodeStore;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import ca.ulaval.glo4003.trotti.domain.trip.values.RidePermitId;
import ca.ulaval.glo4003.trotti.domain.trip.values.ScooterId;
import java.time.LocalDateTime;

public class TripApplicationService {

    private final UnlockCodeService unlockCodeService;
    private final TravelerRepository travelerRepository;
    private final StationRepository stationRepository;
    private final ScooterRepository scooterRepository;
    private final TripRepository tripRepository;
    private final UnlockCodeStore unlockCodeStore;

    public TripApplicationService(
            TravelerRepository travelerRepository,
            StationRepository stationRepository,
            ScooterRepository scooterRepository,
            TripRepository tripRepository,
            UnlockCodeService unlockCodeService,
            UnlockCodeStore unlockCodeStore) {
        this.unlockCodeService = unlockCodeService;
        this.travelerRepository = travelerRepository;
        this.stationRepository = stationRepository;
        this.tripRepository = tripRepository;
        this.scooterRepository = scooterRepository;
        this.unlockCodeStore = unlockCodeStore;
    }

    public void startTrip(Idul idul, RidePermitId ridePermitId, UnlockCode unlockCodeValue,
            Location location, SlotNumber slotNumber) {
        unlockCodeService.validateCode(unlockCodeValue, idul);
        Traveler traveler = travelerRepository.findByIdul(idul);
        Station station = stationRepository.findByLocation(location);
        ScooterId scooterId = station.getScooter(slotNumber);

        Scooter scooter = scooterRepository.findById(scooterId);
        scooter.undock(LocalDateTime.now());

        traveler.startTraveling(LocalDateTime.now(), ridePermitId, scooterId);

        unlockCodeStore.revoke(idul);
        travelerRepository.update(traveler);
        scooterRepository.save(scooter);
        stationRepository.save(station);

    }

    public void endTrip(Idul idul, SlotNumber slotNumber, Location location) {
        Station station = stationRepository.findByLocation(location);
        ScooterId scooterId = station.getScooter(slotNumber);
        Scooter scooter = scooterRepository.findById(scooterId);
        Traveler traveler = travelerRepository.findByIdul(idul);

        scooter.dockAt(location, LocalDateTime.now());
        Trip completetrip = traveler.stopTraveling(LocalDateTime.now());

        travelerRepository.update(traveler);
        scooterRepository.save(scooter);
        stationRepository.save(station);
        tripRepository.save(completetrip);
    }
}
