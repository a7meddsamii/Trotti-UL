package ca.ulaval.glo4003.trotti.commons.exceptionmappers;

import ca.ulaval.glo4003.trotti.commons.ExceptionResponseFactory;
import ca.ulaval.glo4003.trotti.commons.exceptions.InvalidParameterException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidParameterExceptionMapper implements ExceptionMapper<InvalidParameterException> {

    @Override
    public Response toResponse(InvalidParameterException exception) {
        return ExceptionResponseFactory.errorResponse(Response.Status.BAD_REQUEST,
                exception.getMessage());
    }
}
