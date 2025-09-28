package ca.ulaval.glo4003.trotti.infrastructure.sessions;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public record SessionDTO(
        @JsonProperty("semester_code") String semesterCode,
        @JsonProperty("start_date") LocalDate startDate,
        @JsonProperty("end_date") LocalDate endDate
) {}
