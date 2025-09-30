package ca.ulaval.glo4003.trotti.api.exceptionmappers;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExceptionResponseFactoryTest {
    private static final String AN_ERROR_MESSAGE = "Invalid account";

    @Test
    void givenStatus_whenErrorResponse_thenResponseHasExpectedStatus() {
        Response response = ExceptionResponseFactory.errorResponse(Response.Status.BAD_REQUEST,
                AN_ERROR_MESSAGE);

        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void givenMessage_whenErrorResponse_thenEntityContainsExpectedMessage() {
        Response response =
                ExceptionResponseFactory.errorResponse(Response.Status.CONFLICT, AN_ERROR_MESSAGE);
        ApiErrorResponse errorResponse = (ApiErrorResponse) response.getEntity();

        Assertions.assertEquals(AN_ERROR_MESSAGE, errorResponse.message());
    }

    @Test
    void givenStatusAndMessage_whenErrorResponse_thenResponseIsJson() {
        Response.Status status = Response.Status.BAD_REQUEST;

        Response response = ExceptionResponseFactory.errorResponse(status, AN_ERROR_MESSAGE);

        Assertions.assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
    }
}
