package ca.ulaval.glo4003.trotti.api.exceptionmappers;

import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InvalidParameterExceptionMapperTest {

    private static final String AN_ERROR_MESSAGE = "Invalid input provided";
    private final InvalidParameterExceptionMapper exceptionMapper =
            new InvalidParameterExceptionMapper();

    @Test
    void givenInvalidParameterException_whenToResponse_thenStatusIsBadRequest() {
        InvalidParameterException exception = new InvalidParameterException(AN_ERROR_MESSAGE);

        Response response = exceptionMapper.toResponse(exception);

        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void givenInvalidParameterException_whenToResponse_thenMediaTypeIsJson() {
        InvalidParameterException exception = new InvalidParameterException(AN_ERROR_MESSAGE);

        Response response = exceptionMapper.toResponse(exception);

        Assertions.assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
    }

    @Test
    void givenInvalidParameterException_whenToResponse_thenEntityContainsExpectedMessage() {
        InvalidParameterException exception = new InvalidParameterException(AN_ERROR_MESSAGE);

        Response response = exceptionMapper.toResponse(exception);

        ApiErrorResponse errorResponse = (ApiErrorResponse) response.getEntity();
        Assertions.assertEquals(AN_ERROR_MESSAGE, errorResponse.message());
    }

}
