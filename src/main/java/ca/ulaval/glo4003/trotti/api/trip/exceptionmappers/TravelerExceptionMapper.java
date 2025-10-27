package ca.ulaval.glo4003.trotti.api.trip.exceptionmappers;

import ca.ulaval.glo4003.trotti.api.commons.ExceptionResponseFactory;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.TravelerException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class TravelerExceptionMapper implements ExceptionMapper<TravelerException> {
	@Override
	public Response toResponse(TravelerException exception) {
		return ExceptionResponseFactory.errorResponse(Response.Status.BAD_REQUEST,
													  exception.getMessage());
	}
}