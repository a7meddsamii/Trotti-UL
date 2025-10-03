package ca.ulaval.glo4003.trotti.api.order.exceptionmappers;

import ca.ulaval.glo4003.trotti.api.account.exceptionmappers.ExceptionResponseFactory;
import ca.ulaval.glo4003.trotti.domain.order.exceptions.InvalidOrderException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class InvalidOrderExceptionMapper implements ExceptionMapper<InvalidOrderException> {

    @Override
    public Response toResponse(InvalidOrderException exception) {
        return ExceptionResponseFactory.errorResponse(Response.Status.BAD_REQUEST,
                exception.getMessage());
    }
}
