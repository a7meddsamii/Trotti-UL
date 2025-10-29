package ca.ulaval.glo4003.trotti.commons.exceptionmappers;

import ca.ulaval.glo4003.trotti.commons.ExceptionResponseFactory;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        return ExceptionResponseFactory.errorResponse(Response.Status.NOT_FOUND,
                exception.getMessage());
    }
}
