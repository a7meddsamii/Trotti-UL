package ca.ulaval.glo4003.trotti.account.api.exceptionmappers;


import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthorizationException;
import ca.ulaval.glo4003.trotti.commons.api.exceptionmappers.ExceptionResponseFactory;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class AuthorizationExceptionMapper implements ExceptionMapper<AuthorizationException> {

    @Override
    public Response toResponse(AuthorizationException exception) {
        return ExceptionResponseFactory.errorResponse(Response.Status.UNAUTHORIZED,
                exception.getMessage());
    }
}
