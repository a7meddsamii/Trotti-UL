package ca.ulaval.glo4003.trotti.api.order.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "PaymentInfoRequest",
        description = """
        Informations de paiement pour le checkout.
        Si l'utilisateur possède déjà une méthode de paiement enregistrée, 
        seuls les champs `cvv` sont requis.
        Sinon, tous les champs doivent être fournis.
    """
)
public record PaymentInfoRequest(

        @Schema(
                description = "Numéro de la carte (card number), sans espaces — optionnel si une carte est déjà enregistrée.",
                example = "4111111111111111",
                minLength = 13,
                maxLength = 19,
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String cardNumber,

        @Schema(
                description = "Nom du titulaire de la carte (card holder name) — optionnel si une carte est déjà enregistrée.",
                example = "Jean Tremblay",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String cardHolderName,

        @Schema(
                description = "Date d'expiration au format YYYY-MM (expiration date) — optionnel si une carte est déjà enregistrée.",
                example = "2026-12",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String expirationDate,

        @Schema(
                description = "Code de sécurité CVV (3 ou 4 chiffres) — toujours requis.",
                example = "123",
                minLength = 3,
                maxLength = 4,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String cvv
) {}
