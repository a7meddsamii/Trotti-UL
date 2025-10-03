package ca.ulaval.glo4003.trotti.api.dto.requests;


public record PaymentInfoRequest(
    String cardNumber,
    String cardHolderName,
    String expirationDate,
    String cvv) {
}
