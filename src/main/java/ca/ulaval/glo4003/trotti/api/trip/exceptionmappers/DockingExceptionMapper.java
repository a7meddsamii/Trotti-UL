package ca.ulaval.glo4003.trotti.api.trip.exceptionmappers;

import ca.ulaval.glo4003.trotti.api.commons.ExceptionResponseFactory;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.DockingException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DockingExceptionMapper implements ExceptionMapper<DockingException> {
    @Override
    public Response toResponse(DockingException exception) {
        return ExceptionResponseFactory.errorResponse(Response.Status.BAD_REQUEST,
                exception.getMessage());
    }
}
