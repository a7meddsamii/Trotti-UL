package ca.ulaval.glo4003.trotti.order.api.exceptionmappers;

import ca.ulaval.glo4003.trotti.commons.ExceptionResponseFactory;
import ca.ulaval.glo4003.trotti.payment.domain.exceptions.InvalidPaymentRequestException;
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
