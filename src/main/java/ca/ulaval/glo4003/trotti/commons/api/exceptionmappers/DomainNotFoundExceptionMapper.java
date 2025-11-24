package ca.ulaval.glo4003.trotti.commons.api.exceptionmappers;

import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DomainNotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        return ExceptionResponseFactory.errorResponse(Response.Status.NOT_FOUND,
                exception.getMessage());
    }
}
