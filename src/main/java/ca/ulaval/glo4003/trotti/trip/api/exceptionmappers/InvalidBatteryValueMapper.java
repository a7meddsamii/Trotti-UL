package ca.ulaval.glo4003.trotti.trip.api.exceptionmappers;

import ca.ulaval.glo4003.trotti.commons.api.exceptionmappers.ExceptionResponseFactory;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.InvalidBatteryValue;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidBatteryValueMapper implements ExceptionMapper<InvalidBatteryValue> {
    @Override
    public Response toResponse(InvalidBatteryValue exception) {
        return ExceptionResponseFactory.errorResponse(Response.Status.BAD_REQUEST,
                exception.getMessage());
    }
}
