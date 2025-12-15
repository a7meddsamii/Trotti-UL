package ca.ulaval.glo4003.trotti.trip.infrastructure.filter;

import ca.ulaval.glo4003.trotti.trip.domain.values.TripHistorySearchCriteria;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.TripRecord;
import java.time.LocalDate;

public class TripHistoryFilter {

    public boolean matches(TripRecord trip, TripHistorySearchCriteria criteria) {
        LocalDate startDate = criteria.getStartDate();
        LocalDate endDate = criteria.getEndDate();

        LocalDate tripStart = trip.startTime().toLocalDate();
        LocalDate tripEnd = trip.endTime().toLocalDate();

        return trip.idul().equals(criteria.getIdul()) && !tripStart.isBefore(startDate)
                && !tripEnd.isAfter(endDate);
    }
}
