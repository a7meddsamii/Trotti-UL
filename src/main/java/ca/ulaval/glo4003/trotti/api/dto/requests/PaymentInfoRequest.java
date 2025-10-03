package ca.ulaval.glo4003.trotti.api.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record PaymentInfoRequest(
    @NotBlank(message = "Card number is required") String cardNumber,
    @NotBlank(message = "Card holder name is required") String cardHolderName,
    @NotBlank(message = "Expiration date is required") String expirationDate,
    @NotBlank(message = "CVV is required") String cvv) {
}
