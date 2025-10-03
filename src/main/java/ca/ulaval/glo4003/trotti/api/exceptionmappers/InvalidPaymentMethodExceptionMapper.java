package ca.ulaval.glo4003.trotti.api.exceptionmappers;

import ca.ulaval.glo4003.trotti.domain.payment.exceptions.InvalidPaymentMethodException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class InvalidPaymentMethodExceptionMapper implements ExceptionMapper<InvalidPaymentMethodException> {

    @Override
    public Response toResponse(InvalidPaymentMethodException exception) {
        return ExceptionResponseFactory.errorResponse(
                Response.Status.BAD_REQUEST,
                exception.getMessage()
        );
    }
}
