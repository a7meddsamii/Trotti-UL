package ca.ulaval.glo4003.trotti.order.domain.values;

import java.time.LocalDate;
import java.util.Objects;

public class Session {
    private final Semester semester;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Session(Semester semester, LocalDate startDate, LocalDate endDate) {
        this.semester = semester;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Semester getSemester() {
        return semester;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean contains(LocalDate date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Session session = (Session) o;
        return semester.equals(session.semester) && startDate.equals(session.startDate)
                && endDate.equals(session.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(semester, startDate, endDate);
    }

    @Override
    public String toString() {
        return semester.getCode().toString()
                + String.format("%02d", getStartDate().getYear() % 100);
    }
}
