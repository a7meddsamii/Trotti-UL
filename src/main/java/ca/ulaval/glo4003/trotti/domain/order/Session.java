package ca.ulaval.glo4003.trotti.domain.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.Objects;

public class Session {
    private final String semesterCode;
    private final LocalDate startDate;
    private final LocalDate endDate;

    @JsonCreator
    public Session(
            @JsonProperty("semester_code") String semesterCode,
            @JsonProperty("start_date") LocalDate startDate,
            @JsonProperty("end_date") LocalDate endDate
    ) {
        this.semesterCode = semesterCode;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Session email = (Session) o;
        return semesterCode.equals(email.semesterCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(semesterCode);
    }

    @Override
    public String toString() {
        return semesterCode;
    }

    public String getSemesterCode() {
        return semesterCode;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
