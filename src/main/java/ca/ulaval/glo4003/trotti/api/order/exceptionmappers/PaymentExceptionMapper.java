package ca.ulaval.glo4003.trotti.api.order.exceptionmappers;

import ca.ulaval.glo4003.trotti.api.account.exceptionmappers.ExceptionResponseFactory;
import ca.ulaval.glo4003.trotti.domain.payment.exceptions.PaymentException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class PaymentExceptionMapper implements ExceptionMapper<PaymentException> {

    @Override
    public Response toResponse(PaymentException exception) {
        return ExceptionResponseFactory.errorResponse(Response.Status.PAYMENT_REQUIRED,
                exception.getMessage());
    }
}
