package ca.ulaval.glo4003.trotti.api.exceptionmappers;

import ca.ulaval.glo4003.trotti.domain.commons.exceptions.AlreadyExistsException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AlreadyExistsExceptionMapperTest {
    private static final String AN_ERROR_MESSAGE = "Resource already exists";
    private final AlreadyExistsExceptionMapper exceptionMapper = new AlreadyExistsExceptionMapper();

    @Test
    void givenAlreadyExistsException_whenToResponse_thenStatusIsConflict() {
        AlreadyExistsException exception = new AlreadyExistsException(AN_ERROR_MESSAGE);

        Response response = exceptionMapper.toResponse(exception);

        Assertions.assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
    }

    @Test
    void givenAlreadyExistsException_whenToResponse_thenMediaTypeIsJson() {
        AlreadyExistsException exception = new AlreadyExistsException(AN_ERROR_MESSAGE);

        Response response = exceptionMapper.toResponse(exception);

        Assertions.assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
    }

    @Test
    void givenAlreadyExistsException_whenToResponse_thenEntityContainsExpectedMessage() {
        AlreadyExistsException exception = new AlreadyExistsException(AN_ERROR_MESSAGE);

        Response response = exceptionMapper.toResponse(exception);

        ApiErrorResponse errorResponse = (ApiErrorResponse) response.getEntity();
        Assertions.assertEquals(AN_ERROR_MESSAGE, errorResponse.message());
    }

}
