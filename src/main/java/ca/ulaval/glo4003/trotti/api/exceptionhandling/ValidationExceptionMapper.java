package ca.ulaval.glo4003.trotti.api.exceptionhandling;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper
        implements ExceptionMapper<jakarta.validation.ConstraintViolationException> {
    @Override
    public Response toResponse(jakarta.validation.ConstraintViolationException exception) {
        var error = exception.getConstraintViolations().stream()
                .map(v -> new FieldError(v.getPropertyPath().toString(), v.getMessage())).toList();

        var errorBody = new ErrorResponse("Invalid Request", error);

        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON)
                .entity(errorBody).build();
    }

    public record FieldError(String field, String reason) {}

    public record ErrorResponse(String message, java.util.List<FieldError> errors) {}
}
