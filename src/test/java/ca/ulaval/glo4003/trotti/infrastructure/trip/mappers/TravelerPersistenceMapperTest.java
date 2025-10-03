package ca.ulaval.glo4003.trotti.infrastructure.trip.mappers;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.order.Session;
import ca.ulaval.glo4003.trotti.domain.order.values.Semester;
import ca.ulaval.glo4003.trotti.domain.trip.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.Traveler;
import ca.ulaval.glo4003.trotti.fixtures.TravelerFixture;
import ca.ulaval.glo4003.trotti.infrastructure.trip.records.RidePermitRecord;
import ca.ulaval.glo4003.trotti.infrastructure.trip.records.TravelerRecord;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TravelerPersistenceMapperTest {

    private static final Idul IDUL = Idul.from("IDUL");
    private static final Email EMAIL = Email.from("john.doe@ulaval.ca");
    private static final Id PERMIT_ID = Id.randomId();
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
        assertPermitsEquals(traveler.getRidePermits(), record.ridePermits());
    }

    @Test
    void givenTravelerRecordWithoutPermit_whenToDomain_thenReturnCorrespondingTraveler() {
        TravelerRecord record = new TravelerRecord(IDUL, EMAIL, List.of());

        Traveler traveler = travelerMapper.toDomain(record);

        assertTravelersEqual(traveler, record);
        Assertions.assertTrue(traveler.getRidePermits().isEmpty());
    }

    @Test
    void givenTravelerRecordWithPermit_whenToDomain_thenReturnCorrespondingTraveler() {
        TravelerRecord record = new TravelerRecord(IDUL, EMAIL, RIDE_PERMIT_RECORDS);

        Traveler traveler = travelerMapper.toDomain(record);

        assertTravelersEqual(traveler, record);
        assertPermitsEquals(traveler.getRidePermits(), record.ridePermits());
    }

    void assertTravelersEqual(Traveler traveler, TravelerRecord record) {
        Assertions.assertEquals(traveler.getIdul(), record.idul());
        Assertions.assertEquals(traveler.getEmail(), record.email());
        Assertions.assertEquals(traveler.getRidePermits().size(), record.ridePermits().size());
    }

    void assertPermitsEquals(List<RidePermit> ridePermits,
            List<RidePermitRecord> ridePermitRecords) {
        Assertions.assertEquals(ridePermits.get(0).getId(), ridePermitRecords.get(0).permitId());
        Assertions.assertEquals(ridePermits.get(0).getIdul(), ridePermitRecords.get(0).idul());
        Assertions.assertEquals(ridePermits.get(0).getSession(),
                ridePermitRecords.get(0).session());
    }
}
