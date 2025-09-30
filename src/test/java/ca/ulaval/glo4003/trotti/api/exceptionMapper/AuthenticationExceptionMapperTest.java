package ca.ulaval.glo4003.trotti.api.exceptionMapper;

import ca.ulaval.glo4003.trotti.domain.account.exceptions.AuthenticationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AuthenticationExceptionMapperTest {
    private static final String AN_ERROR_MESSAGE = "Invalid credentials";

    private final AuthenticationExceptionMapper exceptionMapper =
            new AuthenticationExceptionMapper();

    @Test
    void givenAuthenticationException_whenToResponse_thenStatusIsUnauthorized() {
        AuthenticationException exception = new AuthenticationException(AN_ERROR_MESSAGE);

        Response response = exceptionMapper.toResponse(exception);

        Assertions.assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    @Test
    void givenAuthenticationException_whenToResponse_thenMediaTypeIsJson() {
        AuthenticationException exception = new AuthenticationException(AN_ERROR_MESSAGE);

        Response response = exceptionMapper.toResponse(exception);

        Assertions.assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
    }

    @Test
    void givenAuthenticationException_whenToResponse_thenEntityContainsExpectedMessage() {
        AuthenticationException exception = new AuthenticationException(AN_ERROR_MESSAGE);

        Response response = exceptionMapper.toResponse(exception);

        ApiErrorResponse errorResponse = (ApiErrorResponse) response.getEntity();
        Assertions.assertEquals(AN_ERROR_MESSAGE, errorResponse.message());
    }
}
