package ca.ulaval.glo4003.trotti.fleet.api.exceptionmappers;

import ca.ulaval.glo4003.trotti.commons.api.exceptionmappers.ExceptionResponseFactory;
import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.InsufficientScootersInTransitException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InsufficientScootersInTransitExceptionMapper
        implements ExceptionMapper<InsufficientScootersInTransitException> {

    @Override
    public Response toResponse(InsufficientScootersInTransitException exception) {
        return ExceptionResponseFactory.errorResponse(Response.Status.BAD_REQUEST,
                exception.getMessage());
    }
}
