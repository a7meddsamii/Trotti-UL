package ca.ulaval.glo4003.trotti.api.order.mappers;

import ca.ulaval.glo4003.trotti.api.order.dto.requests.PaymentInfoRequest;
import ca.ulaval.glo4003.trotti.api.order.dto.responses.TransactionResponse;
import ca.ulaval.glo4003.trotti.application.order.dto.PaymentInfoDto;
import ca.ulaval.glo4003.trotti.application.order.dto.TransactionDto;
import ca.ulaval.glo4003.trotti.domain.payment.exceptions.InvalidPaymentRequestException;

import java.time.YearMonth;

public class OrderApiMapper {

    public PaymentInfoDto toDto(PaymentInfoRequest paymentInfoRequest) {
        if (paymentInfoRequest == null) {
            throw new InvalidPaymentRequestException("Invalid payment info request");
        }

        return new PaymentInfoDto(paymentInfoRequest.cardNumber(),
                paymentInfoRequest.cardHolderName(),
                paymentInfoRequest.expirationDate() == null ? null
                        : YearMonth.parse(paymentInfoRequest.expirationDate()),
                paymentInfoRequest.cvv());
    }

    public TransactionResponse toTransactionResponse(TransactionDto transactionDto) {
        return new TransactionResponse(transactionDto.id().toString(),
                transactionDto.status().toString(), transactionDto.timestamp().toString(),
                transactionDto.amount().toString(), transactionDto.description());
    }
}
