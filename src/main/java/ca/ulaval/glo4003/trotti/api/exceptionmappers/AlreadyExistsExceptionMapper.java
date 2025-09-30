package ca.ulaval.glo4003.trotti.api.exceptionmappers;

import ca.ulaval.glo4003.trotti.domain.commons.exceptions.AlreadyExistsException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class AlreadyExistsExceptionMapper implements ExceptionMapper<AlreadyExistsException> {
    @Override
    public Response toResponse(AlreadyExistsException exception) {
        return ExceptionResponseFactory.errorResponse(Response.Status.CONFLICT,
                exception.getMessage());
    }
}
