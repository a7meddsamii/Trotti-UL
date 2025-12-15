package ca.ulaval.glo4003.trotti.trip.application;

import ca.ulaval.glo4003.trotti.trip.domain.entities.TripHistory;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TripQueryRepository;
import ca.ulaval.glo4003.trotti.trip.domain.values.TripHistorySearchCriteria;

public class TripQueryApplicationService {

    private final TripQueryRepository tripQueryRepository;

    public TripQueryApplicationService(TripQueryRepository tripQueryRepository) {
        this.tripQueryRepository = tripQueryRepository;
    }

    public TripHistory getTripHistory(TripHistorySearchCriteria criteria) {
        return tripQueryRepository.findAllBySearchCriteria(criteria);
    }
}
