package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.entities.CompletedTripTest;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Trip;
import ca.ulaval.glo4003.trotti.trip.domain.entities.TripHistory;
import ca.ulaval.glo4003.trotti.trip.domain.entities.TripTest;
import ca.ulaval.glo4003.trotti.trip.domain.values.TripHistorySearchCriteria;
import ca.ulaval.glo4003.trotti.trip.domain.values.TripStatus;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.TripPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.TripRecord;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class InMemoryTripRepositoryTest {

    private static final Idul MATCHING_TRAVELER_ID = Idul.from("abcd");
    private static final Idul NON_MATCHING_TRAVELER_ID = Idul.from("efgh");
    private static final TripStatus STATUS = TripStatus.COMPLETED;
    private Trip trip;
    private TripRecord tripRecord;
    private TripPersistenceMapper mapper;
    private InMemoryTripRepository repository;

    private void setUpTripRepository() {
        trip = Mockito.mock(Trip.class);
        tripRecord = Mockito.mock(TripRecord.class);
        Mockito.when(tripRecord.idul()).thenReturn(MATCHING_TRAVELER_ID);
        Mockito.when(tripRecord.tripStatus()).thenReturn(STATUS);
        mapper = Mockito.mock(TripPersistenceMapper.class);
        Mockito.when(mapper.toRecord(Mockito.any())).thenReturn(tripRecord);
        Mockito.when(mapper.toDomain(Mockito.any())).thenReturn(trip);
        Mockito.when(trip.getIdul()).thenReturn(MATCHING_TRAVELER_ID);
        Mockito.when(trip.getStatus()).thenReturn(STATUS);
        repository = new InMemoryTripRepository(Clock.systemDefaultZone(), mapper);
    }

    private void setUpTripQueryRepository() {
        Clock clock = Clock.fixed(
                LocalDate.of(2025, 1, 10)
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant(),
                ZoneId.systemDefault()
        );

        mapper = new TripPersistenceMapper();
        repository = new InMemoryTripRepository(clock, mapper);
    }

    @Test
    void givenTrip_whenExists_thenTrue() {
        setUpTripRepository();
        repository.save(trip);

        boolean result = repository.exists(trip.getIdul(), trip.getStatus());

        Assertions.assertTrue(result);
    }

    @Test
    void givenTripThatDoesNotExist_whenExists_thenFalse() {
        setUpTripRepository();
        boolean result = repository.exists(trip.getIdul(), trip.getStatus());

        Assertions.assertFalse(result);
    }

    @Test
    void givenTrip_whenFindByIdulAndStatus_thenShouldReturnTrip() {
        setUpTripRepository();
        repository.save(trip);

        List<Trip> result = repository.findBy(trip.getIdul(), trip.getStatus());

        Assertions.assertEquals(trip, result.getFirst());
    }

    @Test
    void givenFirstTripFromTraveler_whenSave_thenShouldSaveTripInNewList() {
        setUpTripRepository();
        repository.save(trip);

        List<Trip> result = repository.findAllByIdul(MATCHING_TRAVELER_ID);
        Assertions.assertEquals(List.of(trip), result);
    }

    @Test
    void givenNoTripsFromTraveler_whenGetTravelerTrips_thenReturnEmptyList() {
        setUpTripRepository();
        List<Trip> result = repository.findAllByIdul(MATCHING_TRAVELER_ID);

        Assertions.assertEquals(List.of(), result);
    }

    @Test
    void givenSearchCriteria_whenFindAllBySearchCriteria_thenReturnsExpectedTripHistory() {
        setUpTripQueryRepository();
        LocalDateTime inRangeStartTime = LocalDateTime.of(2025, 1, 2, 10, 0);
        LocalDateTime inRangeEndTime = LocalDateTime.of(2025, 1, 2, 11, 0);
        LocalDateTime outOfRangeStartTime = LocalDateTime.of(2024, 1, 2, 10, 0);
        LocalDateTime outOfRangeEndTime = LocalDateTime.of(2024, 1, 2, 11, 0);
        saveTrip(MATCHING_TRAVELER_ID, inRangeStartTime, inRangeEndTime);
        saveTrip(MATCHING_TRAVELER_ID, inRangeStartTime, inRangeEndTime);
        saveTrip(NON_MATCHING_TRAVELER_ID, inRangeStartTime, inRangeEndTime);
        saveTrip(MATCHING_TRAVELER_ID, outOfRangeStartTime, outOfRangeEndTime);
        int expectedCompletedTripsSize = 2;
        TripHistorySearchCriteria criteria = TripHistorySearchCriteria.builder()
                .withIdul(MATCHING_TRAVELER_ID)
                .withStartDate(LocalDate.of(2025, 1, 1))
                .withEndDate(LocalDate.of(2025, 1, 31))
                .build();

        TripHistory history = repository.findAllBySearchCriteria(criteria);

        Assertions.assertEquals(expectedCompletedTripsSize, history.getCompletedTrips().size());
    }

    @Test
    void givenNullStartDateAndEndDate_whenFindAllBySearchCriteria_thenUsesDefaultStartDateAndEndDate() {
        setUpTripQueryRepository();
        LocalDateTime inDefaultRangeStart = LocalDateTime.of(2024, 12, 5, 10, 0);
        LocalDateTime inDefaultRangeEnd = LocalDateTime.of(2024, 12, 5, 11, 0);
        LocalDateTime outOfDefaultRangeStart = LocalDateTime.of(2025, 1, 5, 10, 0);
        LocalDateTime outOfDefaultRangeEnd = LocalDateTime.of(2025, 1, 5, 11, 0);
        saveTrip(MATCHING_TRAVELER_ID, inDefaultRangeStart, inDefaultRangeEnd);
        saveTrip(MATCHING_TRAVELER_ID, inDefaultRangeStart, inDefaultRangeEnd);
        saveTrip(MATCHING_TRAVELER_ID, outOfDefaultRangeStart, outOfDefaultRangeEnd);
        saveTrip(NON_MATCHING_TRAVELER_ID, inDefaultRangeStart, inDefaultRangeEnd);
        int expectedCompletedTripsSize = 2;
        TripHistorySearchCriteria criteria = TripHistorySearchCriteria.builder()
                .withIdul(MATCHING_TRAVELER_ID)
                .withStartDate(null)
                .withEndDate(null)
                .build();

        TripHistory history = repository.findAllBySearchCriteria(criteria);

        Assertions.assertEquals(expectedCompletedTripsSize, history.getCompletedTrips().size());
    }

    private void saveTrip(Idul idul, LocalDateTime startTime, LocalDateTime endTime) {
        trip = Trip.start(TripTest.A_RIDE_PERMIT_ID, idul, TripTest.A_SCOOTER_ID, startTime,
                CompletedTripTest.A_START_LOCATION);
        trip.complete(CompletedTripTest.AN_END_LOCATION, endTime);
        repository.save(trip);
    }
}
