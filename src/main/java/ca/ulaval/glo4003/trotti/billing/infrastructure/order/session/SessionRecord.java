package ca.ulaval.glo4003.trotti.billing.infrastructure.order.session;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public record SessionRecord(
        @JsonProperty("semester_code") String semesterCode,
        @JsonProperty("start_date") @JsonFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
        @JsonProperty("end_date") @JsonFormat(pattern = "yyyy-MM-dd") LocalDate endDate
) {}
