package ca.ulaval.glo4003.trotti.trip.application;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;

import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;
import ca.ulaval.glo4003.trotti.trip.application.dto.EndTripDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.StartTripDto;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Trip;
import ca.ulaval.glo4003.trotti.trip.domain.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.trip.domain.events.TripCompletedEvent;
import ca.ulaval.glo4003.trotti.trip.domain.events.UnlockCodeRequestedEvent;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.TripException;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.RidePermitGateway;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.ScooterRentalGateway;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TripRepository;
import ca.ulaval.glo4003.trotti.trip.domain.store.UnlockCodeStore;
import ca.ulaval.glo4003.trotti.trip.domain.values.*;

import java.time.Clock;
import java.time.LocalDateTime;

public class TripApplicationService {

    private final UnlockCodeStore unlockCodeStore;
    private final TripRepository tripRepository;
    private final RidePermitGateway ridePermitGateway;
    private final ScooterRentalGateway scooterRentalGateway;
    private final EventBus eventBus;
    private final Clock clock;

    public TripApplicationService(UnlockCodeStore unlockCodeStore,
                                  TripRepository tripRepository,
                                  RidePermitGateway ridePermitGateway,
                                  ScooterRentalGateway scooterRentalGateway,
                                  EventBus eventBus,
                                  Clock clock) {
        this.unlockCodeStore = unlockCodeStore;
        this.tripRepository = tripRepository;
        this.ridePermitGateway = ridePermitGateway;
        this.scooterRentalGateway = scooterRentalGateway;
        this.eventBus = eventBus;
        this.clock = clock;
    }

    public void generateUnlockCode(Idul idul, RidePermitId ridePermitId) {
        boolean isOwnerOfRidePermit = ridePermitGateway.isOwnerOfRidePermit(idul, ridePermitId);

        if (!isOwnerOfRidePermit) {
            throw new NotFoundException("Ride permit not found for this traveler");
        }

        UnlockCode unlockCode = unlockCodeStore.generateOrGet(idul, ridePermitId, clock);

        eventBus.publish(new UnlockCodeRequestedEvent(idul,
                ridePermitId.toString(),
                unlockCode.toString(),
                unlockCode.getExpiresAt()));
    }

    public void startTrip(StartTripDto tripDto) {
        unlockCodeStore.validate(tripDto.idul(), tripDto.ridePermitId(), tripDto.unlockCode());

        if (tripRepository.exists(tripDto.idul(), TripStatus.ONGOING)) {
            throw new TripException("Traveler already has an ongoing trip");
        }

        ScooterId scooterId = scooterRentalGateway.retrieveScooter(tripDto.location(), tripDto.slotNumber());

        Trip onGoingTrip = Trip.start(tripDto.ridePermitId(),
                tripDto.idul(),
                scooterId,
                LocalDateTime.now(clock),
                tripDto.location());

        tripRepository.save(onGoingTrip);
        unlockCodeStore.revoke(tripDto.idul(), tripDto.ridePermitId());
    }

    public void endTrip(EndTripDto tripDto) {
        if (!tripRepository.exists(tripDto.idul(), TripStatus.ONGOING)) {
            throw new TripException("Traveler has no ongoing trip to end");
        }

        Trip trip = tripRepository.findBy(tripDto.idul(), TripStatus.ONGOING).getFirst();

        scooterRentalGateway.returnScooter(tripDto.location(), trip.getScooterId(), tripDto.slotNumber());
        trip.complete(tripDto.location(), LocalDateTime.now(clock));
        tripRepository.save(trip);

        eventBus.publish(new TripCompletedEvent(trip.getIdul(),
                trip.getRidePermitId().toString(),
                trip.getScooterId().toString(),
                trip.getStartTime(),
                trip.getEndTime(),
                trip.getStartLocation().toString(),
                trip.getEndLocation().toString()));
    }
}
