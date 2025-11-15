package ca.ulaval.glo4003.trotti.account.api.exceptionmappers;


import ca.ulaval.glo4003.trotti.account.domain.exceptions.UnableToCreateAccountException;
import ca.ulaval.glo4003.trotti.commons.api.exceptionmappers.ExceptionResponseFactory;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UnableToCreateAccountExceptionMapper implements ExceptionMapper<UnableToCreateAccountException> {
    
    @Override
    public Response toResponse(UnableToCreateAccountException exception) {
        return ExceptionResponseFactory.errorResponse(Response.Status.BAD_REQUEST,
                exception.getMessage());
    }
}
