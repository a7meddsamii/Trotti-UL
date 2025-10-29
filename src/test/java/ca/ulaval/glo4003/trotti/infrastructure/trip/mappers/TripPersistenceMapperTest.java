package ca.ulaval.glo4003.trotti.infrastructure.trip.mappers;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Trip;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.TripPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.TripRecord;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TripPersistenceMapperTest {

    private static final LocalDateTime START_TIME = LocalDateTime.now();
    private static final LocalDateTime END_TIME = START_TIME.plusHours(1);
    private static final ScooterId SCOOTER_ID = ScooterId.randomId();
    private static final RidePermitId RIDE_PERMIT_ID = RidePermitId.randomId();
    private static final Idul IDUL = Idul.from("abcd");

    private TripPersistenceMapper mapper;

    @BeforeEach
    public void setup() {
        mapper = new TripPersistenceMapper();
    }

    @Test
    void givenOngoingTrip_whenToRecord_thenReturnEquivalentTripRecord() {
        Trip trip = new Trip(START_TIME, RIDE_PERMIT_ID, IDUL, SCOOTER_ID);

        TripRecord record = mapper.toRecord(trip);

        Assertions.assertEquals(trip.getStartTime(), record.startTime());
        Assertions.assertEquals(trip.getRidePermitId(), record.ridePermitId());
        Assertions.assertEquals(trip.getTravelerIdul(), record.travelerIdul());
        Assertions.assertEquals(trip.getScooterId(), record.scooterId());
        Assertions.assertEquals(trip.getEndTime(), record.endTime());
    }

    @Test
    void givenCompletedTrip_whenToRecord_thenReturnEquivalentTripRecord() {
        Trip trip = new Trip(START_TIME, RIDE_PERMIT_ID, IDUL, SCOOTER_ID).end(END_TIME);

        TripRecord record = mapper.toRecord(trip);

        Assertions.assertEquals(trip.getStartTime(), record.startTime());
        Assertions.assertEquals(trip.getRidePermitId(), record.ridePermitId());
        Assertions.assertEquals(trip.getTravelerIdul(), record.travelerIdul());
        Assertions.assertEquals(trip.getScooterId(), record.scooterId());
        Assertions.assertEquals(trip.getEndTime(), record.endTime());
    }

    @Test
    void givenTripRecord_whenToDomain_thenReturnEquivalentTrip() {
        TripRecord record = new TripRecord(START_TIME, RIDE_PERMIT_ID, IDUL, SCOOTER_ID, END_TIME);

        Trip trip = mapper.toDomain(record);

        Assertions.assertEquals(record.startTime(), trip.getStartTime());
        Assertions.assertEquals(record.ridePermitId(), trip.getRidePermitId());
        Assertions.assertEquals(record.travelerIdul(), trip.getTravelerIdul());
        Assertions.assertEquals(record.scooterId(), trip.getScooterId());
        Assertions.assertEquals(record.endTime(), trip.getEndTime());
    }

}
