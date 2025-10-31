package ca.ulaval.glo4003.trotti.order.api.exceptionmappers;

import ca.ulaval.glo4003.trotti.commons.api.exceptionmappers.ExceptionResponseFactory;
import ca.ulaval.glo4003.trotti.order.domain.exceptions.InvalidOrderException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class InvalidOrderExceptionMapper implements ExceptionMapper<InvalidOrderException> {

    @Override
    public Response toResponse(InvalidOrderException exception) {
        return ExceptionResponseFactory.errorResponse(Response.Status.BAD_REQUEST,
                exception.getMessage());
    }
}
