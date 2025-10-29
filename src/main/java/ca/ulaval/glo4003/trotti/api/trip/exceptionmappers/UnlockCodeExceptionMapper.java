package ca.ulaval.glo4003.trotti.api.trip.exceptionmappers;

import ca.ulaval.glo4003.trotti.api.commons.ExceptionResponseFactory;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.UnlockCodeException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UnlockCodeExceptionMapper implements ExceptionMapper<UnlockCodeException> {

    @Override
    public Response toResponse(UnlockCodeException exception) {
        return ExceptionResponseFactory.errorResponse(Response.Status.UNAUTHORIZED,
                exception.getMessage());
    }

}
