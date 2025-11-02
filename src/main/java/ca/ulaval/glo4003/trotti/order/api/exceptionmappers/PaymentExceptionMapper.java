package ca.ulaval.glo4003.trotti.order.api.exceptionmappers;

import ca.ulaval.glo4003.trotti.commons.api.exceptionmappers.ExceptionResponseFactory;
import ca.ulaval.glo4003.trotti.payment.domain.exceptions.PaymentException;
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
