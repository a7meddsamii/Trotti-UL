package ca.ulaval.glo4003.trotti.trip.infrastructure.mappers;

import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Trip;
import ca.ulaval.glo4003.trotti.trip.domain.values.*;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.TripPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.TripRecord;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TripPersistenceMapperTest {

    private static final LocalDateTime START_TIME = LocalDateTime.now();
    private static final LocalDateTime END_TIME = START_TIME.plusHours(1);
    private static final ScooterId SCOOTER_ID = ScooterId.randomId();
    private static final RidePermitId RIDE_PERMIT_ID = RidePermitId.randomId();
    private static final Location START_LOCATION = Location.of("Building", "SpotName");
    private static final Location END_LOCATION = START_LOCATION;
    private static final Idul IDUL = Idul.from("abcd");
    private static final TripId TRIP_ID = TripId.randomId();
    private static final TripStatus TRIP_STATUS = TripStatus.COMPLETED;

    private TripPersistenceMapper mapper = new TripPersistenceMapper();

    @Test
    void givenJustStartedTrip_whenToRecord_thenReturnEquivalentTripRecord() {
        Trip startedTrip = Trip.start(RIDE_PERMIT_ID, IDUL, SCOOTER_ID, START_TIME, START_LOCATION);

        TripRecord record = mapper.toRecord(startedTrip);

        Assertions.assertEquals(startedTrip.getTripId(), record.tripId());
        Assertions.assertEquals(startedTrip.getStartTime(), record.startTime());
        Assertions.assertEquals(startedTrip.getRidePermitId(), record.ridePermitId());
        Assertions.assertEquals(startedTrip.getIdul(), record.idul());
        Assertions.assertEquals(startedTrip.getScooterId(), record.scooterId());
        Assertions.assertNull(record.endTime());
        Assertions.assertNull(record.endLocation());
    }

    @Test
    void givenCompletedTrip_whenToRecord_thenReturnEquivalentTripRecord() {
        Trip trip = Trip.start(RIDE_PERMIT_ID, IDUL, SCOOTER_ID, START_TIME, START_LOCATION);
        trip.complete(END_LOCATION, END_TIME);

        TripRecord record = mapper.toRecord(trip);

        Assertions.assertEquals(trip.getTripId(), record.tripId());
        Assertions.assertEquals(trip.getRidePermitId(), record.ridePermitId());
        Assertions.assertEquals(trip.getIdul(), record.idul());
        Assertions.assertEquals(trip.getScooterId(), record.scooterId());
        Assertions.assertEquals(trip.getStartLocation(), record.startLocation());
        Assertions.assertEquals(trip.getStartTime(), record.startTime());
        Assertions.assertEquals(trip.getEndTime(), record.endTime());
        Assertions.assertEquals(trip.getEndLocation(), record.endLocation());
    }

    @Test
    void givenTripRecord_whenToDomain_thenReturnEquivalentTrip() {
        TripRecord record = new TripRecord(TRIP_ID, IDUL, RIDE_PERMIT_ID, SCOOTER_ID, START_TIME,
                START_LOCATION, END_TIME, END_LOCATION, TRIP_STATUS);

        Trip trip = mapper.toDomain(record);

        Assertions.assertEquals(record.tripId(), trip.getTripId());
        Assertions.assertEquals(record.startLocation(), trip.getStartLocation());
        Assertions.assertEquals(record.endLocation(), trip.getEndLocation());
        Assertions.assertEquals(record.startTime(), trip.getStartTime());
        Assertions.assertEquals(record.endTime(), trip.getEndTime());
        Assertions.assertEquals(record.ridePermitId(), trip.getRidePermitId());
        Assertions.assertEquals(record.idul(), trip.getIdul());
        Assertions.assertEquals(record.scooterId(), trip.getScooterId());
        Assertions.assertEquals(record.tripStatus(), trip.getStatus());
    }

}
