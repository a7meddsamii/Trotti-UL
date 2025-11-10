package ca.ulaval.glo4003.trotti.commons.api.exceptionmappers;

import ca.ulaval.glo4003.trotti.commons.domain.exceptions.SessionException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class SessionExceptionMapper implements ExceptionMapper<SessionException>  {
		
		@Override
		public Response toResponse(SessionException exception) {
			return ExceptionResponseFactory.errorResponse(Response.Status.BAD_REQUEST,
														  exception.getMessage());
		}
}
