package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.entities.CompletedTrip;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Trip;
import ca.ulaval.glo4003.trotti.trip.domain.entities.TripHistory;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TripQueryRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TripCommandRepository;
import ca.ulaval.glo4003.trotti.trip.domain.values.TripHistorySearchCriteria;
import ca.ulaval.glo4003.trotti.trip.domain.values.TripId;
import ca.ulaval.glo4003.trotti.trip.domain.values.TripStatus;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.TripPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.TripRecord;

import java.time.Clock;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTripCommandRepository implements TripCommandRepository, TripQueryRepository {
    private Map<TripId, TripRecord> tripTable = new HashMap<>();

    private final Clock clock;
    private final TripPersistenceMapper mapper;

    public InMemoryTripCommandRepository(Clock clock, TripPersistenceMapper mapper) {
        this.mapper = mapper;
        this.clock = clock;
    }

    @Override
    public boolean exists(Idul idul, TripStatus status) {
        return tripTable.values().stream().anyMatch(tripRecord -> tripRecord.idul().equals(idul)
                && tripRecord.tripStatus().equals(status));

    }

    @Override
    public void save(Trip trip) {
        TripRecord tripRecord = mapper.toRecord(trip);
        tripTable.put(tripRecord.tripId(), tripRecord);
    }

    @Override
    public List<Trip> findAllByIdul(Idul idul) {
        return tripTable.values().stream().filter(tripRecord -> tripRecord.idul().equals(idul))
                .map(mapper::toDomain).toList();
    }

    @Override
    public List<Trip> findBy(Idul idul, TripStatus tripStatus) {
        return tripTable.values().stream()
                .filter(tripRecord -> tripRecord.idul().equals(idul)
                        && tripRecord.tripStatus().equals(tripStatus))
                .map(mapper::toDomain).toList();
    }

    @Override
    public TripHistory findAllBySearchCriteria(TripHistorySearchCriteria criteria) {
        LocalDate now = LocalDate.now(clock);

        LocalDate defaultStart = now.minusMonths(1).withDayOfMonth(1);
        LocalDate defaultEnd = now.minusMonths(1).withDayOfMonth(
                now.minusMonths(1).lengthOfMonth()
        );

        LocalDate startDate = criteria.getStartDate();
        LocalDate endDate = criteria.getEndDate();

        if (startDate == null && endDate == null) {
            startDate = defaultStart;
            endDate = defaultEnd;
        }

        final LocalDate filterStart = startDate;
        final LocalDate filterEnd = endDate;

        List<CompletedTrip> completedTrips = tripTable.values().stream()
                .filter(trip -> {
                    boolean matchesIdul = trip.idul().equals(criteria.getIdul());
                    LocalDate tripStart = trip.startTime().toLocalDate();
                    LocalDate tripEnd = trip.endTime().toLocalDate();
                    boolean matchesStartDate = filterStart == null || !tripStart.isBefore(filterStart);
                    boolean matchesEndDate = filterEnd == null || !tripEnd.isAfter(filterEnd);
                    return matchesIdul && matchesStartDate && matchesEndDate;
                })
                .map(mapper::toCompletedTrip)
                .toList();

        return new TripHistory(completedTrips);
    }
}
