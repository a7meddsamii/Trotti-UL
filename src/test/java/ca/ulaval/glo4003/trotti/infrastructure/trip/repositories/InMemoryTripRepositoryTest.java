package ca.ulaval.glo4003.trotti.infrastructure.trip.repositories;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Trip;
import ca.ulaval.glo4003.trotti.infrastructure.trip.mappers.TripPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.TripRecord;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class InMemoryTripRepositoryTest {

    private static final Idul TRAVELER_ID = Idul.from("abcd");

    private Trip trip;
    private TripRecord tripRecord;
    private TripPersistenceMapper mapper;
    private InMemoryTripRepository repository;

    @BeforeEach
    void setUp() {
        trip = Mockito.mock(Trip.class);
        tripRecord = Mockito.mock(TripRecord.class);
        Mockito.when(tripRecord.travelerIdul()).thenReturn(TRAVELER_ID);
        mapper = Mockito.mock(TripPersistenceMapper.class);
        Mockito.when(mapper.toRecord(Mockito.any())).thenReturn(tripRecord);
        Mockito.when(mapper.toDomain(Mockito.any())).thenReturn(trip);
        repository = new InMemoryTripRepository(mapper);
    }

    @Test
    void givenFirstTripFromTraveler_whenSave_thenShouldSaveTripInNewList() {

        repository.save(trip);

        List<Trip> result = repository.getTravelerTrips(TRAVELER_ID);
        Mockito.verify(tripRecord).travelerIdul();
        Assertions.assertEquals(List.of(trip), result);
    }

    @Test
    void givenMultipleTripsFromTraveler_whenSave_thenShouldSaveToSameList() {

        repository.save(trip);

        repository.save(trip);

        List<Trip> result = repository.getTravelerTrips(TRAVELER_ID);
        Assertions.assertEquals(List.of(trip, trip), result);
    }

    @Test
    void givenNoTripsFromTraveler_whenGetTravelerTrips_thenReturnEmptyList() {
        List<Trip> result = repository.getTravelerTrips(TRAVELER_ID);

        Assertions.assertEquals(List.of(), result);
    }

}
