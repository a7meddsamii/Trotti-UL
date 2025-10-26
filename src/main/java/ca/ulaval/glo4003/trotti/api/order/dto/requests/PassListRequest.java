package ca.ulaval.glo4003.trotti.api.order.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Schema(
        name = "PassListRequest",
        description = """
        Requête contenant la liste des passes à ajouter au panier (cart).
        Chaque élément représente un pass avec sa durée maximale, sa session et sa fréquence de facturation.
        La liste doit contenir au moins un pass.
    """
)
public record PassListRequest(

        @Schema(
                description = "Liste des passes à ajouter (chaque élément décrit un pass).",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotEmpty(message = "List must not be empty")
        @NotNull(message = "Please provide at least one pass")
        @Valid
        List<PassRequest> passes
) {

    @Schema(
            name = "PassRequest",
            description = """
            Détails d'un pass : durée maximale de déplacement quotidien (en minutes),
            session du pass et fréquence de facturation.
        """
    )
    public record PassRequest(

            @Schema(
                    description = "Durée maximale quotidienne en minutes (ex: 30).",
                    example = "30",
                    minimum = "10",
                    requiredMode = Schema.RequiredMode.REQUIRED
            )
            @Min(value = 10, message = "Maximum Daily Travel Time must be at least 10 mins")
            int maximumDailyTravelTime,

            @Schema(
                    description = "Session du pass (ex: 'MORNING', 'EVENING').",
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
}
