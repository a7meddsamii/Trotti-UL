package ca.ulaval.glo4003.trotti.application.trip;

import ca.ulaval.glo4003.trotti.application.trip.dto.EndTripDto;
import ca.ulaval.glo4003.trotti.application.trip.dto.StartTripDto;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Scooter;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Trip;
import ca.ulaval.glo4003.trotti.domain.trip.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.TravelerRepository;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.TripRepository;
import ca.ulaval.glo4003.trotti.domain.trip.services.UnlockCodeService;
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

    public void startTrip(StartTripDto startTripDto) {
        unlockCodeService.revoke(startTripDto.unlockCode());
        Traveler traveler = travelerRepository.findByIdul(startTripDto.idul());
        Station station = stationRepository.findByLocation(startTripDto.location());
        ScooterId scooterId = station.getScooter(startTripDto.slotNumber());

        LocalDateTime startTime = LocalDateTime.ofInstant(clock.instant(), clock.getZone());
        Scooter scooter = scooterRepository.findById(scooterId);
        scooter.undock(startTime);

        traveler.startTraveling(startTime, startTripDto.ridePermitId(), scooterId);

        travelerRepository.update(traveler);
        scooterRepository.save(scooter);
        stationRepository.save(station);

    }

    public void endTrip(EndTripDto endTripDto) {
        Traveler traveler = travelerRepository.findByIdul(endTripDto.idul());
        Station station = stationRepository.findByLocation(endTripDto.location());
        ScooterId scooterId = station.getScooter(endTripDto.slotNumber());
        Scooter scooter = scooterRepository.findById(scooterId);
        LocalDateTime endTime = LocalDateTime.ofInstant(clock.instant(), clock.getZone());

        scooter.dockAt(endTripDto.location(), endTime);
        Trip completetrip = traveler.stopTraveling(endTime);

        travelerRepository.update(traveler);
        scooterRepository.save(scooter);
        stationRepository.save(station);
        tripRepository.save(completetrip);
    }
}
