package ca.ulaval.glo4003.trotti.trip.domain.values;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;

import java.time.LocalDate;

public class TripHistorySearchCriteria {

    private final Idul idul;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private TripHistorySearchCriteria(Idul idul, LocalDate startDate, LocalDate endDate) {
        this.idul = idul;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Idul getIdul() {
        return idul;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public static class Builder {
        private Idul idul;
        private LocalDate startDate;
        private LocalDate endDate;

        public Builder withIdul(Idul idul) {
            this.idul = idul;
            return this;
        }

        public Builder withStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder withEndDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public TripHistorySearchCriteria build() {
            if (idul == null) {
                throw new InvalidParameterException("Idul must be provided");
            }


            if (startDate != null && endDate != null) {
                if (startDate.isAfter(endDate)) {
                    throw new InvalidParameterException("startDate must be before endDate");
                }

                return new TripHistorySearchCriteria(idul, startDate, endDate);
            }

            if (startDate != null) {
                endDate = startDate.plusMonths(1).minusDays(1);
            }
            else if (endDate != null) {
                startDate = endDate.minusMonths(1).plusDays(1);
            }
            else {
                startDate = LocalDate.now()
                        .minusMonths(1)
                        .withDayOfMonth(1);
                endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
            }

            return new TripHistorySearchCriteria(idul, startDate, endDate);
        }
    }

}
