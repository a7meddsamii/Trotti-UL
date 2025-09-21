package ca.ulaval.glo4003.trotti.api.exceptionhandling;

import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ApiExceptionHandler implements ExceptionMapper<InvalidParameterException> {

    @Override
    public Response toResponse(InvalidParameterException exception) {
        ApiErrorResponse errorResponse =
                new ApiErrorResponse(exception.getErrorType(), exception.getMessage());

        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON)
                .entity(errorResponse).build();
    }
}
