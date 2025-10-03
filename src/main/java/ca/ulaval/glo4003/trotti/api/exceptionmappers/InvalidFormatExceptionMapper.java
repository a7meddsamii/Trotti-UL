package ca.ulaval.glo4003.trotti.api.exceptionmappers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.LocalDate;

@Provider
public class InvalidFormatExceptionMapper implements ExceptionMapper<InvalidFormatException> {

    @Override
    public Response toResponse(InvalidFormatException exception) {
        if (exception.getTargetType() == LocalDate.class) {
            return ExceptionResponseFactory.errorResponse(Response.Status.BAD_REQUEST,
                    "Invalid date format for birthDate. Expected format: yyyy-MM-dd");
        }

        return ExceptionResponseFactory.errorResponse(Response.Status.BAD_REQUEST,
                "Invalid input format: " + exception.getOriginalMessage());
    }
}
