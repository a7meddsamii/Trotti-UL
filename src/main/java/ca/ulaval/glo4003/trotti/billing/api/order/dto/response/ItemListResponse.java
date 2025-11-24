package ca.ulaval.glo4003.trotti.billing.api.order.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(
		name = "ItemListResponse",
		description = """
        Réponse retournée après l'ajout ou la consultation des passes dans le panier (cart).
        Contient la liste des passes sélectionnés ainsi que le total à payer.
    """
)
public record ItemListResponse(
		@Schema(description = "Identifiant unique de la commande.", example = "order_987654321")
		String orderId,
		
		@Schema(description = "Montant total de la commande.", example = "39.97")
		String total,
		
		@Schema(description = "Status de la commande.", example = "PENDING")
		String status,
		
		@Schema(description = "Liste des items présents dans le panier.")
		List<ItemResponse> items
		) {
	
	@Schema(
			name = "ItemResponse",
			description = """
            Détails d'un item dans le panier : identifiant, durée maximale, session,
            fréquence de facturation et prix individuel.
        """
	)
	public record ItemResponse(
			
			@Schema(description = "Identifiant unique de l'item.", example = "pass_123456789")
			String id,
			
			@Schema(description = "Durée maximale quotidienne de l'item (en minutes).",example = "30")
			String maximumDailyTravelTime,
			
			@Schema(description = "Session associée à l'item.", example = "H26")
			String session,
			
			@Schema(description = "Fréquence de facturation de l'item", example = "MONTHLY")
			String billingFrequency,
			
			@Schema(description = "Prix de l'item individuel.", example = "12.99")
			String price
	) {}
}