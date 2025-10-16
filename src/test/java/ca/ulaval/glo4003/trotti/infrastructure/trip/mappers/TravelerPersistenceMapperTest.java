package ca.ulaval.glo4003.trotti.infrastructure.trip.mappers;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.order.values.Semester;
import ca.ulaval.glo4003.trotti.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.domain.trip.entities.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Trip;
import ca.ulaval.glo4003.trotti.domain.trip.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.fixtures.TravelerFixture;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.RidePermitRecord;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.TravelerRecord;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.TripRecord;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TravelerPersistenceMapperTest {

    private static final Idul IDUL = Idul.from("IDUL");
    private static final Email EMAIL = Email.from("john.doe@ulaval.ca");
    private static final Id PERMIT_ID = Id.randomId();
    private static final Id TRIP_ID = Id.randomId();
    private static final Id SCOOTER_ID = Id.randomId();
    private static final LocalDateTime START_DATE = LocalDateTime.of(2019, Month.JANUARY, 1, 0, 0);
    private static final List<TripRecord> TRIPS =
            List.of(new TripRecord(TRIP_ID, START_DATE, PERMIT_ID, IDUL, SCOOTER_ID));
    private static final List<RidePermitRecord> RIDE_PERMIT_RECORDS =
            List.of(new RidePermitRecord(PERMIT_ID, IDUL, new Session(Semester.FALL,
                    LocalDate.parse("2025-09-02"), LocalDate.parse("2025-12-12"))));

    private TravelerPersistenceMapper travelerMapper;
    private TravelerFixture travelerFixture;

    @BeforeEach
    void setup() {
        travelerMapper = new TravelerPersistenceMapper();
        travelerFixture = new TravelerFixture();
    }

    @Test
    void givenTravelerWithoutPermit_whenToRecord_thenReturnCorrespondingTravelerRecord() {
        Traveler traveler = travelerFixture.withNoRidePermit().build();

        TravelerRecord record = travelerMapper.toRecord(traveler);

        assertTravelersEqual(traveler, record);
    }

    @Test
    void givenTravelerWithPermit_whenToRecord_thenReturnCorrespondingTravelerRecord() {
        Traveler traveler = travelerFixture.withRidePermit().build();

        TravelerRecord record = travelerMapper.toRecord(traveler);

        assertTravelersEqual(traveler, record);
        assertPermitsEquals(traveler.getWalletPermits(), record.ridePermits());
    }

    @Test
    void givenTravelerRecordWithoutPermit_whenToDomain_thenReturnCorrespondingTraveler() {
        TravelerRecord record = new TravelerRecord(IDUL, EMAIL, List.of(), List.of());

        Traveler traveler = travelerMapper.toDomain(record);

        assertTravelersEqual(traveler, record);
        Assertions.assertTrue(traveler.getWalletPermits().isEmpty());
    }

    @Test
    void givenTravelerRecordWithPermit_whenToDomain_thenReturnCorrespondingTraveler() {
        TravelerRecord record = new TravelerRecord(IDUL, EMAIL, RIDE_PERMIT_RECORDS, TRIPS);

        Traveler traveler = travelerMapper.toDomain(record);

        assertTravelersEqual(traveler, record);
        assertPermitsEquals(traveler.getWalletPermits(), record.ridePermits());
    }

    @Test
    void givenTravelerWithoutTrips_whenToRecord_thenReturnCorrespondingTravelerRecord() {
        Traveler traveler = travelerFixture.withoutTrips().build();

        TravelerRecord record = travelerMapper.toRecord(traveler);

        assertTravelersEqual(traveler, record);
    }

    @Test
    void givenTravelerWithTrips_whenToRecord_thenReturnCorrespondingTravelerRecord() {
        Trip trip = Mockito.mock(Trip.class);
        Traveler traveler = travelerFixture.withTrips(trip).build();

        TravelerRecord record = travelerMapper.toRecord(traveler);

        assertTravelersEqual(traveler, record);
    }

    @Test
    void givenTravelerRecordWithTrips_whenToDomain_thenReturnCorrespondingTraveler() {
        TripRecord trip = Mockito.mock(TripRecord.class);
        TravelerRecord record = new TravelerRecord(IDUL, EMAIL, List.of(), List.of(trip));

        Traveler traveler = travelerMapper.toDomain(record);

        assertTravelersEqual(traveler, record);
    }

    void assertTravelersEqual(Traveler traveler, TravelerRecord record) {
        Assertions.assertEquals(traveler.getIdul(), record.idul());
        Assertions.assertEquals(traveler.getEmail(), record.email());
        Assertions.assertEquals(traveler.getWalletPermits().size(), record.ridePermits().size());
    }

    void assertPermitsEquals(List<RidePermit> ridePermits,
            List<RidePermitRecord> ridePermitRecords) {
        Assertions.assertEquals(ridePermits.get(0).getId(), ridePermitRecords.get(0).permitId());
        Assertions.assertEquals(ridePermits.get(0).getIdul(), ridePermitRecords.get(0).idul());
        Assertions.assertEquals(ridePermits.get(0).getSession(),
                ridePermitRecords.get(0).session());
    }
}
