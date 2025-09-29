package ca.ulaval.glo4003.trotti.infrastructure.sessions.repository;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SessionRecord(
        @JsonProperty("semester_code") String semesterCode,
        @JsonProperty("start_date") String startDate,
        @JsonProperty("end_date") String endDate
) {}
