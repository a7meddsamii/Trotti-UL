package ca.ulaval.glo4003.trotti.domain.order;

import java.time.LocalDate;
import java.util.Objects;

public class Session {
    private final String code;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Session(String code, LocalDate startDate, LocalDate endDate) {
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getCode() {
        return code;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Session session = (Session) o;
        return code.equals(session.getCode()) && startDate.equals(session.getStartDate())
                && endDate.equals(session.getEndDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, startDate, endDate);
    }

    public boolean includes(LocalDate date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
}
