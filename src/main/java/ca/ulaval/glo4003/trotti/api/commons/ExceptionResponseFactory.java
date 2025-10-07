package ca.ulaval.glo4003.trotti.api.commons;

import ca.ulaval.glo4003.trotti.api.commons.dto.ApiErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class ExceptionResponseFactory {

    public static Response errorResponse(Response.Status status, String message) {
        ApiErrorResponse response = new ApiErrorResponse(message);

        return Response.status(status).type(MediaType.APPLICATION_JSON).entity(response).build();
    }
}
