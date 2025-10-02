package ca.ulaval.glo4003.trotti.application.order.mappers;

import ca.ulaval.glo4003.trotti.application.order.dto.TransactionDto;
import ca.ulaval.glo4003.trotti.domain.payment.values.Money;
import ca.ulaval.glo4003.trotti.domain.payment.values.Transaction;
import ca.ulaval.glo4003.trotti.domain.payment.values.TransactionStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TransactionMapperTest {

    private TransactionMapper transactionMapper = new TransactionMapper();

    @Test
    void givenTransaction_whenToDto_thenReturnTransactionDto() {
        Transaction transaction =
                new Transaction(TransactionStatus.SUCCESS, Money.zeroCad(), "Test Transaction");

        TransactionDto transactionDto = transactionMapper.toDto(transaction);

        Assertions.assertEquals(transaction.getId(), transactionDto.id());
        Assertions.assertEquals(transaction.getStatus(), transactionDto.status());
        Assertions.assertEquals(transaction.getTimestamp(), transactionDto.timestamp());
        Assertions.assertEquals(transaction.getAmount(), transactionDto.amount());
        Assertions.assertEquals(transaction.getDescription(), transactionDto.description());
    }
}
