package ca.ulaval.glo4003.trotti.commons.api.exceptionmappers;

import ca.ulaval.glo4003.trotti.commons.domain.exceptions.EmployeeNotAuthorized;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class EmployeeNotAuthorizedExceptionMapper
        implements ExceptionMapper<EmployeeNotAuthorized> {

    @Override
    public Response toResponse(EmployeeNotAuthorized exception) {
        return ExceptionResponseFactory.errorResponse(Response.Status.UNAUTHORIZED,
                exception.getMessage());
    }
}
