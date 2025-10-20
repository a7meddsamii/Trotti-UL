package ca.ulaval.glo4003.trotti.api.order.mappers;

import ca.ulaval.glo4003.trotti.api.order.dto.requests.PaymentInfoRequest;
import ca.ulaval.glo4003.trotti.api.order.dto.responses.TransactionResponse;
import ca.ulaval.glo4003.trotti.application.order.dto.PaymentInfoDto;
import ca.ulaval.glo4003.trotti.application.order.dto.TransactionDto;
import ca.ulaval.glo4003.trotti.domain.commons.payment.values.money.Currency;
import ca.ulaval.glo4003.trotti.domain.commons.payment.values.money.Money;
import ca.ulaval.glo4003.trotti.domain.commons.payment.values.transaction.TransactionId;
import ca.ulaval.glo4003.trotti.domain.commons.payment.values.transaction.TransactionStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderApiMapperTest {

    private static final String CARD_NUMBER = "4111111111111111";
    private static final String CARD_HOLDER_NAME = "John Doe";
    private static final String EXPIRATION_DATE_STRING = "2025-12";
    private static final YearMonth EXPIRATION_DATE = YearMonth.of(2025, 12);
    private static final String CVV = "123";
    private static final BigDecimal AMOUNT_VALUE = new BigDecimal(45);
    private static final Currency CURRENCY = Currency.CAD;
    private static final String TRANSACTION_DESCRIPTION = "Payment completed";
    private static final TransactionStatus TRANSACTION_STATUS = TransactionStatus.SUCCESS;

    private final OrderApiMapper mapper = new OrderApiMapper();

    @Test
    void givenValidPaymentInfoRequest_whenToDto_thenMapsCorrectly() {
        PaymentInfoRequest request =
                new PaymentInfoRequest(CARD_NUMBER, CARD_HOLDER_NAME, EXPIRATION_DATE_STRING, CVV);

        PaymentInfoDto dto = mapper.toDto(request);

        Assertions.assertEquals(CARD_NUMBER, dto.cardNumber());
        Assertions.assertEquals(CARD_HOLDER_NAME, dto.cardHolderName());
        Assertions.assertEquals(EXPIRATION_DATE, dto.expirationDate());
        Assertions.assertEquals(CVV, dto.cvv());
    }

    @Test
    void givenNullExpirationDate_whenToDto_thenDtoHasNullExpirationDate() {
        PaymentInfoRequest request =
                new PaymentInfoRequest(CARD_NUMBER, CARD_HOLDER_NAME, null, CVV);

        PaymentInfoDto dto = mapper.toDto(request);

        Assertions.assertNull(dto.expirationDate());
    }

    @Test
    void givenTransactionDto_whenToTransactionResponse_thenMapsCorrectly() {
        TransactionDto dto = new TransactionDto(TransactionId.randomId(), TRANSACTION_STATUS,
                LocalDateTime.now(), Money.of(AMOUNT_VALUE, CURRENCY), TRANSACTION_DESCRIPTION);

        TransactionResponse response = mapper.toTransactionResponse(dto);

        Assertions.assertEquals(dto.id().toString(), response.transactionId());
        Assertions.assertEquals(TRANSACTION_STATUS.name(), response.status());
        Assertions.assertEquals(dto.timestamp().toString(), response.timestamp());
        Assertions.assertEquals(Money.of(AMOUNT_VALUE, CURRENCY).toString(), response.amount());
        Assertions.assertEquals(TRANSACTION_DESCRIPTION, response.description());
    }
}
