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
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import ca.ulaval.glo4003.trotti.domain.trip.values.RidePermitId;
import ca.ulaval.glo4003.trotti.domain.trip.values.ScooterId;

import java.time.Clock;
import java.time.LocalDateTime;

public class TripApplicationService {

    private final UnlockCodeService unlockCodeService;
    private final TravelerRepository travelerRepository;
    private final StationRepository stationRepository;
    private final ScooterRepository scooterRepository;
    private final TripRepository tripRepository;
    private final Clock clock;

    public TripApplicationService(
            TravelerRepository travelerRepository,
            StationRepository stationRepository,
            ScooterRepository scooterRepository,
            TripRepository tripRepository,
            UnlockCodeService unlockCodeService,
            Clock clock) {
        this.unlockCodeService = unlockCodeService;
        this.travelerRepository = travelerRepository;
        this.stationRepository = stationRepository;
        this.tripRepository = tripRepository;
        this.scooterRepository = scooterRepository;
        this.clock = clock;
    }

    public void startTrip(Idul idul, RidePermitId ridePermitId, UnlockCode unlockCodeValue,
            Location location, SlotNumber slotNumber) {
        Traveler traveler = travelerRepository.findByIdul(idul);
        Station station = stationRepository.findByLocation(location);
        unlockCodeService.validateAndRevoke(unlockCodeValue, idul);
        ScooterId scooterId = station.getScooter(slotNumber);

        Scooter scooter = scooterRepository.findById(scooterId);
        scooter.undock(LocalDateTime.now(clock));

        traveler.startTraveling(LocalDateTime.now(clock), ridePermitId, scooterId);

        travelerRepository.update(traveler);
        scooterRepository.save(scooter);
        stationRepository.save(station);

    }

    public void endTrip(Idul idul, SlotNumber slotNumber, Location location) {
        Traveler traveler = travelerRepository.findByIdul(idul);
        Station station = stationRepository.findByLocation(location);
        ScooterId scooterId = station.getScooter(slotNumber);
        Scooter scooter = scooterRepository.findById(scooterId);

        scooter.dockAt(location, LocalDateTime.now(clock));
        Trip completetrip = traveler.stopTraveling(LocalDateTime.now(clock));

        travelerRepository.update(traveler);
        scooterRepository.save(scooter);
        stationRepository.save(station);
        tripRepository.save(completetrip);
    }
}
