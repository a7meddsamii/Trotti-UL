package ca.ulaval.glo4003.trotti.trip.application;

import ca.ulaval.glo4003.trotti.trip.application.dto.EndTripDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.StartTripDto;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Scooter;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Station;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Trip;
import ca.ulaval.glo4003.trotti.trip.domain.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TravelerRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TripRepository;
import ca.ulaval.glo4003.trotti.trip.domain.services.UnlockCodeService;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
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
        Traveler traveler = travelerRepository.findByIdul(startTripDto.idul());
        Station station = stationRepository.findByLocation(startTripDto.location());
        ScooterId scooterId = station.getScooter(startTripDto.slotNumber());
        Scooter scooter = scooterRepository.findById(scooterId);
        LocalDateTime startTime = LocalDateTime.ofInstant(clock.instant(), clock.getZone());

        traveler.startTraveling(startTime, startTripDto.ridePermitId(), scooterId);
        scooter.undock(startTime);

        unlockCodeService.revoke(startTripDto.unlockCode());
        travelerRepository.update(traveler);
        scooterRepository.save(scooter);
        stationRepository.save(station);
    }

    public void endTrip(EndTripDto endTripDto) {
        Traveler traveler = travelerRepository.findByIdul(endTripDto.idul());
        Station station = stationRepository.findByLocation(endTripDto.location());
        ScooterId scooterId = traveler.getUsedScooter();
        Scooter scooter = scooterRepository.findById(scooterId);
        LocalDateTime endTime = LocalDateTime.ofInstant(clock.instant(), clock.getZone());

        scooter.dockAt(endTripDto.location(), endTime);
        station.returnScooter(endTripDto.slotNumber(), scooterId);
        Trip completetrip = traveler.stopTraveling(endTime);

        travelerRepository.update(traveler);
        scooterRepository.save(scooter);
        stationRepository.save(station);
        tripRepository.save(completetrip);
    }
}
