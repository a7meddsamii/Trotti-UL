package ca.ulaval.glo4003.trotti.api.commons.exceptionmappers;

import ca.ulaval.glo4003.trotti.api.commons.ExceptionResponseFactory;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
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
