package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Trip;
import ca.ulaval.glo4003.trotti.trip.domain.values.TripStatus;
import ca.ulaval.glo4003.trotti.trip.infrastructure.filter.TripHistoryFilter;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.TripPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.TripRecord;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class InMemoryTripRepositoryTest {

    private static final Idul MATCHING_TRAVELER_ID = Idul.from("abcd");
    private static final TripStatus STATUS = TripStatus.COMPLETED;
    private Trip trip;
    private TripRecord tripRecord;
    private TripPersistenceMapper mapper;
    private TripHistoryFilter filter;
    private InMemoryTripRepository repository;

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


    private void setUpTripRepository() {
        trip = Mockito.mock(Trip.class);
        filter = Mockito.mock(TripHistoryFilter.class);
        tripRecord = Mockito.mock(TripRecord.class);
        Mockito.when(tripRecord.idul()).thenReturn(MATCHING_TRAVELER_ID);
        Mockito.when(tripRecord.tripStatus()).thenReturn(STATUS);
        mapper = Mockito.mock(TripPersistenceMapper.class);
        Mockito.when(mapper.toRecord(Mockito.any())).thenReturn(tripRecord);
        Mockito.when(mapper.toDomain(Mockito.any())).thenReturn(trip);
        Mockito.when(trip.getIdul()).thenReturn(MATCHING_TRAVELER_ID);
        Mockito.when(trip.getStatus()).thenReturn(STATUS);
        repository = new InMemoryTripRepository(mapper, filter);
    }

}
