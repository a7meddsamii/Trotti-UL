package ca.ulaval.glo4003.trotti.trip.api.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.ws.rs.QueryParam;
import java.time.LocalDate;

public record TripQueryRequest(
		@QueryParam("startDate")
		@Schema(description = "Date de début (YYYY-MM-DD)", example = "2025-01-01")
		LocalDate startDate,
		
		@QueryParam("endDate")
		@Schema(description = "Date de fin (YYYY-MM-DD)", example = "2025-01-31")
		LocalDate endDate
) {
}
