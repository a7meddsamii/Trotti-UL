package ca.ulaval.glo4003.trotti.application.order.dto;

import java.time.YearMonth;

public record PaymentInfoDto(String cardNumber, String cardHolderName, YearMonth expirationDate, String cvv) {
}
