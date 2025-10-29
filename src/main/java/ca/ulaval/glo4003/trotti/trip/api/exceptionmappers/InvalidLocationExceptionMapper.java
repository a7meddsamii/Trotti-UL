package ca.ulaval.glo4003.trotti.trip.api.exceptionmappers;

import ca.ulaval.glo4003.trotti.commons.ExceptionResponseFactory;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.InvalidLocationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidLocationExceptionMapper implements ExceptionMapper<InvalidLocationException> {
    @Override
    public Response toResponse(InvalidLocationException exception) {
        return ExceptionResponseFactory.errorResponse(Response.Status.BAD_REQUEST,
                exception.getMessage());
    }
}
