package ca.ulaval.glo4003.trotti.order.api.exceptionmappers;

import ca.ulaval.glo4003.trotti.commons.api.exceptionmappers.ExceptionResponseFactory;
import ca.ulaval.glo4003.trotti.payment.domain.exceptions.InvalidPaymentMethodException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidPaymentMethodExceptionMapper
        implements ExceptionMapper<InvalidPaymentMethodException> {

    @Override
    public Response toResponse(InvalidPaymentMethodException exception) {
        return ExceptionResponseFactory.errorResponse(Response.Status.BAD_REQUEST,
                exception.getMessage());
    }
}
