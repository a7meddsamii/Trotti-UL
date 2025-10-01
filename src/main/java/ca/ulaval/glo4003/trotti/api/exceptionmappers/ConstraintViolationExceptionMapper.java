package ca.ulaval.glo4003.trotti.api.exceptionmappers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.stream.Collectors;

@Provider
public class ConstraintViolationExceptionMapper
        implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        String errors = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        ApiErrorResponse response = new ApiErrorResponse(errors);

        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON)
                .entity(response).build();

    }
}
