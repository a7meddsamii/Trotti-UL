package ca.ulaval.glo4003.trotti.trip.application;

import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.commons.domain.events.trip.TripCompletedEvent;
import ca.ulaval.glo4003.trotti.commons.domain.events.trip.UnlockCodeRequestedEvent;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.application.dto.EndTripDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.StartTripDto;
import ca.ulaval.glo4003.trotti.trip.application.mappers.TripMapper;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Trip;
import ca.ulaval.glo4003.trotti.trip.domain.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.TripException;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.RidePermitGateway;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.ScooterRentalGateway;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TripRepository;
import ca.ulaval.glo4003.trotti.trip.domain.store.UnlockCodeStore;
import ca.ulaval.glo4003.trotti.trip.domain.values.*;
import java.time.Clock;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class TripApplicationServiceTest {

    private static final Idul IDUL = Idul.from("abcd");
    private static final RidePermitId RIDE_PERMIT_ID = RidePermitId.randomId();
    private static final String UNLOCK_CODE = "9999";
    private static final SlotNumber SLOT_NUMBER = new SlotNumber(3);

    private UnlockCodeStore unlockCodeStore;
    private TripRepository tripRepository;
    private RidePermitGateway ridePermitGateway;
    private ScooterRentalGateway scooterRentalGateway;
    private EventBus eventBus;
    private Clock clock;
    private TripMapper tripMapper;
    private TripApplicationService service;

    @BeforeEach
    void setup() {
        unlockCodeStore = Mockito.mock(UnlockCodeStore.class);
        tripRepository = Mockito.mock(TripRepository.class);
        ridePermitGateway = Mockito.mock(RidePermitGateway.class);
        scooterRentalGateway = Mockito.mock(ScooterRentalGateway.class);
        eventBus = Mockito.mock(EventBus.class);
        clock = Clock.systemDefaultZone();
        tripMapper = Mockito.mock(TripMapper.class);

        service = new TripApplicationService(unlockCodeStore, tripRepository, ridePermitGateway,
                scooterRentalGateway, eventBus, clock, tripMapper);
    }

    @Test
    void givenOwnerOfRidePermit_whenGenerateUnlockCode_thenGeneratesAndPublishesAnEvent() {
        UnlockCode mockCode = Mockito.mock(UnlockCode.class);
        Mockito.when(ridePermitGateway.isOwnerOfRidePermit(IDUL, RIDE_PERMIT_ID)).thenReturn(true);
        Mockito.when(unlockCodeStore.get(IDUL, RIDE_PERMIT_ID, clock)).thenReturn(mockCode);
        Mockito.when(mockCode.toString()).thenReturn(UNLOCK_CODE);
        LocalDateTime expiresAt = LocalDateTime.now(clock).plusMinutes(5);
        Mockito.when(mockCode.getExpiresAt()).thenReturn(expiresAt);

        service.generateUnlockCode(IDUL, RIDE_PERMIT_ID);

        Mockito.verify(unlockCodeStore).get(IDUL, RIDE_PERMIT_ID, clock);
        Mockito.verify(eventBus)
                .publish(Mockito.argThat(event -> event instanceof UnlockCodeRequestedEvent e
                        && e.getIdul().equals(IDUL)
                        && e.getRidePermitId().equals(RIDE_PERMIT_ID.toString())
                        && e.getExpirationTime().equals(expiresAt)

                ));
    }

    @Test
    void givenNotOwnerOfRidePermit_whenGenerateUnlockCode_thenThrowsException() {
        Mockito.when(ridePermitGateway.isOwnerOfRidePermit(IDUL, RIDE_PERMIT_ID)).thenReturn(false);

        Executable serviceCall = () -> service.generateUnlockCode(IDUL, RIDE_PERMIT_ID);

        Assertions.assertThrows(NotFoundException.class, serviceCall);
    }

    @Test
    void givenTravelerHasOngoingTrip_whenStartTrip_thenThrowsException() {
        StartTripDto startDto = Mockito.mock(StartTripDto.class);
        Mockito.when(startDto.idul()).thenReturn(IDUL);
        Mockito.when(startDto.ridePermitId()).thenReturn(RIDE_PERMIT_ID);
        Mockito.when(startDto.unlockCode()).thenReturn(UNLOCK_CODE);
        Mockito.when(tripRepository.exists(Mockito.eq(IDUL), Mockito.any(TripStatus.class)))
                .thenReturn(true);
        Mockito.doNothing().when(unlockCodeStore).validate(IDUL, RIDE_PERMIT_ID, UNLOCK_CODE);

        Executable serviceCall = () -> service.startTrip(startDto);

        Assertions.assertThrows(TripException.class, serviceCall);
    }

    @Test
    void givenValidStartTrip_whenStartTrip_thenRetrievesScooterSavesTripAndRevokesUnlockCode() {
        StartTripDto startDto = Mockito.mock(StartTripDto.class);
        Location location = Mockito.mock(Location.class);
        Mockito.when(startDto.idul()).thenReturn(IDUL);
        Mockito.when(startDto.ridePermitId()).thenReturn(RIDE_PERMIT_ID);
        Mockito.when(startDto.unlockCode()).thenReturn(UNLOCK_CODE);
        Mockito.when(startDto.location()).thenReturn(location);
        Mockito.when(startDto.slotNumber()).thenReturn(SLOT_NUMBER);
        Mockito.doNothing().when(unlockCodeStore).validate(IDUL, RIDE_PERMIT_ID, UNLOCK_CODE);
        Mockito.when(tripRepository.exists(Mockito.eq(IDUL), Mockito.any(TripStatus.class)))
                .thenReturn(false);

        ScooterId scooterId = Mockito.mock(ScooterId.class);
        Mockito.when(scooterRentalGateway.retrieveScooter(location, SLOT_NUMBER))
                .thenReturn(scooterId);

        service.startTrip(startDto);

        Mockito.verify(scooterRentalGateway).retrieveScooter(location, SLOT_NUMBER);
        Mockito.verify(tripRepository).save(Mockito.any());
        Mockito.verify(unlockCodeStore).revoke(IDUL, RIDE_PERMIT_ID);
    }

    @Test
    void givenNoOngoingTrip_whenEndTrip_thenThrowsException() {
        EndTripDto endDto = Mockito.mock(EndTripDto.class);
        Location location = Mockito.mock(Location.class);
        Mockito.when(endDto.idul()).thenReturn(IDUL);
        Mockito.when(endDto.location()).thenReturn(location);
        Mockito.when(endDto.slotNumber()).thenReturn(SLOT_NUMBER);
        Mockito.when(tripRepository.exists(Mockito.eq(IDUL), Mockito.any(TripStatus.class)))
                .thenReturn(false);

        Executable serviceCall = () -> service.endTrip(endDto);

        Assertions.assertThrows(TripException.class, serviceCall);
    }

    @Test
    void givenEndTripDto_whenEndTrip_thenPublishesEvent() {
        EndTripDto endDto = Mockito.mock(EndTripDto.class);
        Location endLocation = Mockito.mock(Location.class);
        ScooterId scooterId = ScooterId.randomId();
        LocalDateTime startTime = LocalDateTime.now(clock).minusMinutes(10);
        LocalDateTime endTime = LocalDateTime.now(clock);
        Trip trip = Mockito.mock(Trip.class);
        Mockito.when(trip.getIdul()).thenReturn(IDUL);
        Mockito.when(trip.getRidePermitId()).thenReturn(RIDE_PERMIT_ID);
        Mockito.when(trip.getScooterId()).thenReturn(scooterId);
        Mockito.when(trip.getStartTime()).thenReturn(startTime);
        Mockito.when(trip.getEndTime()).thenReturn(endTime);
        Mockito.when(trip.getStartLocation()).thenReturn(endLocation);
        Mockito.when(trip.getEndLocation()).thenReturn(endLocation);
        Mockito.when(endDto.idul()).thenReturn(IDUL);
        Mockito.when(endDto.location()).thenReturn(endLocation);
        Mockito.when(endDto.slotNumber()).thenReturn(SLOT_NUMBER);
        Mockito.when(tripRepository.exists(IDUL, TripStatus.ONGOING)).thenReturn(true);
        Mockito.when(tripRepository.findBy(IDUL, TripStatus.ONGOING))
                .thenReturn(java.util.List.of(trip));

        service.endTrip(endDto);

        Mockito.verify(eventBus)
                .publish(Mockito.argThat(event -> event instanceof TripCompletedEvent e
                        && e.getIdul().equals(IDUL)
                        && e.getRidePermitId().equals(RIDE_PERMIT_ID.toString())
                        && e.getStartTime().equals(startTime) && e.getEndTime().equals(endTime)
                        && e.getStartLocation().equals(endLocation.toString())
                        && e.getEndLocation().equals(endLocation.toString())));
    }

}
