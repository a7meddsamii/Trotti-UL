package ca.ulaval.glo4003.trotti.api.order.dto.responses;

public record TransactionResponse(
        String transactionId,
        String status,
        String timestamp,
        String amount,
        String description
) {
}
