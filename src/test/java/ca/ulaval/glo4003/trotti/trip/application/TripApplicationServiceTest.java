package ca.ulaval.glo4003.trotti.trip.application;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;
import ca.ulaval.glo4003.trotti.order.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.application.dto.EndTripDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.StartTripDto;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Scooter;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Station;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Trip;
import ca.ulaval.glo4003.trotti.trip.domain.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.trip.domain.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TravelerRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TripRepository;
import ca.ulaval.glo4003.trotti.trip.domain.services.UnlockCodeService;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class TripApplicationServiceTest {
    private static final Idul TRAVELER_IDUL = Idul.from("ulaval");
    private static final RidePermitId RIDE_PERMIT_ID = RidePermitId.randomId();
    private static final Location STATION_LOCATION = Location.of("VACHON", "Entrée Vachon #1");
    private static final SlotNumber SLOT_NUMBER = new SlotNumber(1);
    private static final ScooterId SCOOTER_ID = ScooterId.randomId();
    private static final Instant FIXED_INSTANT = Instant.parse("2025-01-01T10:00:00Z");
    private static final LocalDateTime EXPECTED_TIME =
            LocalDateTime.ofInstant(FIXED_INSTANT, ZoneOffset.UTC);

    private TravelerRepository travelerRepository;
    private StationRepository stationRepository;
    private ScooterRepository scooterRepository;
    private TripRepository tripRepository;
    private UnlockCodeService unlockCodeService;
    private TripApplicationService tripApplicationService;

    private Traveler traveler;
    private Scooter scooter;
    private Station station;
    private Clock clock;
    private UnlockCode unlockCode;
    private StartTripDto startTripDto;
    private EndTripDto endTripDto;
    private Trip ongoingTrip;
    private Trip completedTrip;

    @BeforeEach
    void setUp() {
        travelerRepository = Mockito.mock(TravelerRepository.class);
        stationRepository = Mockito.mock(StationRepository.class);
        scooterRepository = Mockito.mock(ScooterRepository.class);
        tripRepository = Mockito.mock(TripRepository.class);
        unlockCodeService = Mockito.mock(UnlockCodeService.class);

        traveler = Mockito.mock(Traveler.class);
        scooter = Mockito.mock(Scooter.class);
        station = Mockito.mock(Station.class);
        ongoingTrip = Mockito.mock(Trip.class);

        clock = Clock.fixed(FIXED_INSTANT, ZoneOffset.UTC);
        unlockCode = UnlockCode.generateFromTravelerId(TRAVELER_IDUL);

        Mockito.when(travelerRepository.findByIdul(TRAVELER_IDUL)).thenReturn(traveler);
        Mockito.when(stationRepository.findByLocation(STATION_LOCATION)).thenReturn(station);
        Mockito.when(station.getScooter(SLOT_NUMBER)).thenReturn(SCOOTER_ID);
        Mockito.when(scooterRepository.findById(SCOOTER_ID)).thenReturn(scooter);

        Mockito.when(traveler.getOngoingTrip()).thenReturn(Optional.of(ongoingTrip));
        Mockito.when(ongoingTrip.getScooterId()).thenReturn(SCOOTER_ID);
        completedTrip = Mockito.mock(Trip.class);
        Mockito.when(traveler.stopTraveling(Mockito.any(LocalDateTime.class)))
                .thenReturn(completedTrip);

        startTripDto = new StartTripDto(TRAVELER_IDUL, RIDE_PERMIT_ID, unlockCode, STATION_LOCATION,
                SLOT_NUMBER);
        endTripDto = new EndTripDto(TRAVELER_IDUL, STATION_LOCATION, SLOT_NUMBER);

        tripApplicationService = new TripApplicationService(travelerRepository, stationRepository,
                scooterRepository, tripRepository, unlockCodeService, clock);
    }

    @Test
    void givenValidUnlockCode_whenStartTrip_thenTravelerStartsTraveling() {

        tripApplicationService.startTrip(startTripDto);

        Mockito.verify(traveler).startTraveling(Mockito.eq(EXPECTED_TIME),
                Mockito.eq(RIDE_PERMIT_ID), Mockito.eq(SCOOTER_ID));
    }

    @Test
    void givenValidUnlockCode_whenStartTrip_thenScooterIsUndocked() {
        tripApplicationService.startTrip(startTripDto);

        Mockito.verify(scooter).undock(Mockito.eq(EXPECTED_TIME));
    }

    @Test
    void givenValidUnlockCode_whenStartTrip_thenUnlockCodeIsValidatedAndRevoked() {
        tripApplicationService.startTrip(startTripDto);

        Mockito.verify(unlockCodeService).revoke(unlockCode);
    }

    @Test
    void givenValidUnlockCode_whenStartTrip_thenAllRepositoriesAreUpdated() {
        tripApplicationService.startTrip(startTripDto);

        Mockito.verify(travelerRepository).update(traveler);
        Mockito.verify(scooterRepository).save(scooter);
        Mockito.verify(stationRepository).save(station);
    }

    @Test
    void givenNonExistentTraveler_whenStartTrip_thenThrowsException() {
        Mockito.when(travelerRepository.findByIdul(TRAVELER_IDUL))
                .thenThrow(new NotFoundException("Traveler not found"));

        Executable action = () -> tripApplicationService.startTrip(startTripDto);

        Assertions.assertThrows(NotFoundException.class, action);
    }

    @Test
    void givenTravelerWithOngoingTrip_whenEndTrip_thenTravelerStopsTraveling() {

        tripApplicationService.endTrip(endTripDto);

        Mockito.verify(traveler).stopTraveling(Mockito.eq(EXPECTED_TIME));
    }

    @Test
    void givenTravelerWithOngoingTrip_whenEndTrip_thenScooterIsDockedAtStation() {
        Trip completedTrip = Mockito.mock(Trip.class);
        Mockito.when(traveler.stopTraveling(Mockito.eq(EXPECTED_TIME))).thenReturn(completedTrip);

        tripApplicationService.endTrip(endTripDto);

        Mockito.verify(scooter).dockAt(Mockito.eq(STATION_LOCATION), Mockito.eq(EXPECTED_TIME));
    }

    @Test
    void givenValidUnlockCode_whenEndTrip_thenAllRepositoriesAreUpdated() {
        Trip completedTrip = Mockito.mock(Trip.class);
        Mockito.when(traveler.stopTraveling(Mockito.eq(EXPECTED_TIME))).thenReturn(completedTrip);

        tripApplicationService.endTrip(endTripDto);

        Mockito.verify(scooterRepository).save(scooter);
        Mockito.verify(stationRepository).save(station);
        Mockito.verify(travelerRepository).update(traveler);
        Mockito.verify(tripRepository).save(completedTrip);
    }

    @Test
    void givenNonExistentTraveler_whenEndTrip_thenThrowsException() {
        Mockito.when(travelerRepository.findByIdul(TRAVELER_IDUL))
                .thenThrow(new NotFoundException("Traveler not found"));

        Executable action = () -> tripApplicationService.endTrip(endTripDto);

        Assertions.assertThrows(NotFoundException.class, action);
    }
}
