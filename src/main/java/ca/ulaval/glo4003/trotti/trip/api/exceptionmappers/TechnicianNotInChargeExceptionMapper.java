package ca.ulaval.glo4003.trotti.trip.api.exceptionmappers;

import ca.ulaval.glo4003.trotti.commons.api.exceptionmappers.ExceptionResponseFactory;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.TechnicianNotInChargeException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class TechnicianNotInChargeExceptionMapper implements ExceptionMapper<TechnicianNotInChargeException> {

    @Override
    public Response toResponse(TechnicianNotInChargeException exception) {
        return ExceptionResponseFactory.errorResponse(Response.Status.FORBIDDEN,
                exception.getMessage());
    }
}