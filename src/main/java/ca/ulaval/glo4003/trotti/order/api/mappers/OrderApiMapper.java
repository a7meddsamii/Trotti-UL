package ca.ulaval.glo4003.trotti.order.api.mappers;

import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.order.api.dto.requests.PaymentInfoRequest;
import ca.ulaval.glo4003.trotti.order.api.dto.responses.TransactionResponse;
import ca.ulaval.glo4003.trotti.order.application.dto.PaymentInfoDto;
import ca.ulaval.glo4003.trotti.order.application.dto.TransactionDto;
import ca.ulaval.glo4003.trotti.payment.domain.exceptions.InvalidPaymentRequestException;
import java.time.YearMonth;
import org.apache.commons.lang3.StringUtils;

public class OrderApiMapper {

    public PaymentInfoDto toDto(PaymentInfoRequest paymentInfoRequest) {
        if (paymentInfoRequest == null) {
            throw new InvalidPaymentRequestException("Invalid payment info request");
        }

        return new PaymentInfoDto(paymentInfoRequest.cardNumber(),
                paymentInfoRequest.cardHolderName(),
                StringUtils.isBlank(paymentInfoRequest.expirationDate()) ? null
                        : parseYearMonth(paymentInfoRequest.expirationDate()),
                paymentInfoRequest.cvv());
    }

    public TransactionResponse toTransactionResponse(TransactionDto transactionDto) {
        return new TransactionResponse(transactionDto.id().toString(),
                transactionDto.status().toString(), transactionDto.timestamp().toString(),
                transactionDto.amount().toString(), transactionDto.description());
    }

    private YearMonth parseYearMonth(String expirationDate) {
        if (StringUtils.isBlank(expirationDate)) {
            throw new InvalidPaymentRequestException("Expiration date cannot be null or empty");
        }
        try {
            return YearMonth.parse(expirationDate);
        } catch (Exception e) {
            throw new InvalidParameterException("Expiration date must follow pattern yyyy-MM");
        }
    }
}
