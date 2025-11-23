package ca.ulaval.glo4003.trotti.billing.api.order.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Schema(
		name = "ItemRequest",
		description = """
            Détails de l'item : durée maximale de déplacement quotidien (en minutes),
            session du pass et fréquence de facturation.
        """
)
public record ItemRequest(
		@Schema(
				description = "Durée maximale quotidienne en minutes (ex: 30).",
				example = "30",
				minimum = "10",
				requiredMode = Schema.RequiredMode.REQUIRED
		)
		@Min(value = 10, message = "Maximum Daily Travel Time must be at least 10 mins")
		int maximumDailyTravelTime,
		
		@Schema(
				description = "Session de l'item (ex: 'MORNING', 'EVENING').",
				example = "MORNING",
				requiredMode = Schema.RequiredMode.REQUIRED
		)
		@NotBlank(message = "Session is required")
		String session,
		
		@Schema(
				description = "Fréquence de facturation du pass (ex: 'MONTHLY', 'WEEKLY').",
				example = "MONTHLY",
				requiredMode = Schema.RequiredMode.REQUIRED
		)
		@NotBlank(message = "Billing frequency is required")
		String billingFrequency
) {}