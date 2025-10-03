package ca.ulaval.glo4003.trotti.api.exceptionmappers;

import ca.ulaval.glo4003.trotti.domain.payment.exceptions.PaymentDeclinedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class PaymentDeclinedExceptionMapper implements ExceptionMapper<PaymentDeclinedException> {

    @Override
    public Response toResponse(PaymentDeclinedException exception) {
        return ExceptionResponseFactory.errorResponse(
                Response.Status.PAYMENT_REQUIRED,
                exception.getMessage()
        );
    }
}
