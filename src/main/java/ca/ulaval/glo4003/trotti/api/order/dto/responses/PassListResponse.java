package ca.ulaval.glo4003.trotti.api.order.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(
        name = "PassListResponse",
        description = """
        Réponse retournée après l'ajout ou la consultation des passes dans le panier (cart).
        Contient la liste des passes sélectionnés ainsi que le total à payer.
    """
)
public record PassListResponse(

        @Schema(description = "Liste des passes présents dans le panier.")
        List<PassResponse> passes,

        @Schema(description = "Montant total de la commande.", example = "39.97")
        String total
) {

    @Schema(
            name = "PassResponse",
            description = """
            Détails d'un pass dans le panier : identifiant, durée maximale, session,
            fréquence de facturation et prix individuel.
        """
    )
    public record PassResponse(

            @Schema(description = "Identifiant unique du pass.", example = "pass_123456789")
            String id,

            @Schema(description = "Durée maximale quotidienne du pass (en minutes).",example = "30")
            String maximumDailyTravelTime,

            @Schema(description = "Session associée au pass.", example = "H26")
            String session,

            @Schema(description = "Fréquence de facturation du pass", example = "MONTHLY")
            String billingFrequency,

            @Schema(description = "Prix du pass individuel.", example = "12.99")
            String price
    ) {}
}
