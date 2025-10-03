package ca.ulaval.glo4003.trotti.api.order.mappers;

import ca.ulaval.glo4003.trotti.api.order.dto.requests.PaymentInfoRequest;
import ca.ulaval.glo4003.trotti.api.order.dto.responses.TransactionResponse;
import ca.ulaval.glo4003.trotti.application.order.dto.PaymentInfoDto;
import ca.ulaval.glo4003.trotti.application.order.dto.TransactionDto;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.payment.values.Currency;
import ca.ulaval.glo4003.trotti.domain.payment.values.Money;
import ca.ulaval.glo4003.trotti.domain.payment.values.TransactionStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.math.BigDecimal;

class OrderApiMapperTest {

    private final OrderApiMapper mapper = new OrderApiMapper();

    @Test
    void givenValidPaymentInfoRequest_whenToDto_thenMapsCorrectly() {
        PaymentInfoRequest request = new PaymentInfoRequest("4111111111111111",
                "John Doe", "2025-12", "123");

        PaymentInfoDto dto = mapper.toDto(request);

        Assertions.assertEquals("4111111111111111", dto.cardNumber());
        Assertions.assertEquals("John Doe", dto.cardHolderName());
        Assertions.assertEquals(YearMonth.of(2025, 12), dto.expirationDate());
        Assertions.assertEquals("123", dto.cvv());
    }

    @Test
    void givenNullExpirationDate_whenToDto_thenDtoHasNullExpirationDate() {
        PaymentInfoRequest request = new PaymentInfoRequest("4111111111111111",
                "John Doe", null, "123");

        PaymentInfoDto dto = mapper.toDto(request);

        Assertions.assertNull(dto.expirationDate());
    }

    @Test
    void givenTransactionDto_whenToTransactionResponse_thenMapsCorrectly() {
        TransactionDto dto = new TransactionDto(
                Id.randomId(),
                TransactionStatus.SUCCESS,
                LocalDateTime.now(),
                Money.of(new BigDecimal(45), Currency.CAD),
                "Payment completed");

        TransactionResponse response = mapper.toTransactionResponse(dto);

        Assertions.assertEquals(dto.id().toString(), response.transactionId());
        Assertions.assertEquals("SUCCESS", response.status());
        Assertions.assertEquals(dto.timestamp().toString(), response.timestamp());
        Assertions.assertEquals(Money.of(new BigDecimal(45), Currency.CAD).toString(), response.amount());
        Assertions.assertEquals("Payment completed", response.description());
    }
}
