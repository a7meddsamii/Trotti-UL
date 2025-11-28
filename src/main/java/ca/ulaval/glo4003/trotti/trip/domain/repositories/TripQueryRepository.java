package ca.ulaval.glo4003.trotti.trip.domain.repositories;

import ca.ulaval.glo4003.trotti.trip.domain.entities.TripHistory;
import ca.ulaval.glo4003.trotti.trip.domain.values.TripHistorySearchCriteria;

public interface TripQueryRepository {

    TripHistory findAllBySearchCriteria(TripHistorySearchCriteria tripHistorySearchCriteria);
}
