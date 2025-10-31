package ca.ulaval.glo4003.trotti.commons.api.exceptionmappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        return ExceptionResponseFactory.errorResponse(Response.Status.INTERNAL_SERVER_ERROR,
                exception.getMessage());
    }
}
