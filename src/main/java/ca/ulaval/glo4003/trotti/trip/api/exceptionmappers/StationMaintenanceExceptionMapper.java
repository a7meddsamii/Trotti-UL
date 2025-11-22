package ca.ulaval.glo4003.trotti.trip.api.exceptionmappers;

import ca.ulaval.glo4003.trotti.commons.api.exceptionmappers.ExceptionResponseFactory;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.StationMaintenanceException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class StationMaintenanceExceptionMapper
        implements ExceptionMapper<StationMaintenanceException> {

    @Override
    public Response toResponse(StationMaintenanceException exception) {
        return ExceptionResponseFactory.errorResponse(Response.Status.CONFLICT,
                exception.getMessage());
    }
}
