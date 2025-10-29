package ca.ulaval.glo4003.trotti.order.api.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "TransactionResponse",
        description = """
        Réponse renvoyée après une transaction (paiement, remboursement, etc.).
        Contient les informations principales de la transaction exécutée.
    """
)
public record TransactionResponse(

        @Schema(
                description = "Identifiant unique de la transaction.",
                example = "txn_4f23b2e7a9c54a0d8b7d1c2e"
        )
        String transactionId,

        @Schema(
                description = """
            Statut actuel de la transaction.
        """,
                example = "SUCCESS"
        )
        String status,

        @Schema(
                description = "Horodatage ISO-8601 indiquant quand la transaction a été effectuée.",
                example = "2025-10-25T14:32:10Z"
        )
        String timestamp,

        @Schema(
                description = "Montant total de la transaction (en dollars canadiens).",
                example = "19.99"
        )
        String amount,

        @Schema(
                description = "Brève description de la transaction",
                example = "Paiement effectué avec succès avec la carte se terminant par 4242."
        )
        String description
) {}
