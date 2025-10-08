package ca.ulaval.glo4003.trotti.api.order.exceptionmappers;

import ca.ulaval.glo4003.trotti.api.commons.ExceptionResponseFactory;
import ca.ulaval.glo4003.trotti.domain.commons.payment.exceptions.InvalidPaymentRequestException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class InvalidPaymentRequestExceptionMapper
        implements ExceptionMapper<InvalidPaymentRequestException> {

    @Override
    public Response toResponse(InvalidPaymentRequestException exception) {
        return ExceptionResponseFactory.errorResponse(Response.Status.BAD_REQUEST,
                exception.getMessage());
    }
}
