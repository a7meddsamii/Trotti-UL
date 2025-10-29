package ca.ulaval.glo4003.trotti.order.application.dto;

import java.time.YearMonth;

public record PaymentInfoDto(String cardNumber, String cardHolderName, YearMonth expirationDate, String cvv) {
}
