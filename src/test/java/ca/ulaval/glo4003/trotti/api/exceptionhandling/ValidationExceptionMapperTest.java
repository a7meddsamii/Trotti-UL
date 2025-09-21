package ca.ulaval.glo4003.trotti.api.exceptionhandling;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Collections;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ValidationExceptionMapperTest {

    private static final String EXPECTED_ERROR_MESSAGE = "Invalid Request";


    @Test
    void whenConstraintViolation_thenReturns400WithBody() {
        ValidationExceptionMapper mapper = new ValidationExceptionMapper();
        Set<ConstraintViolation<?>> violations = Collections.emptySet();
        ConstraintViolationException exception = new ConstraintViolationException(violations);

        Response response = mapper.toResponse(exception);

        assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
        assertThat(response.getMediaType()).isEqualTo(MediaType.APPLICATION_JSON_TYPE);

        var errorResponse = (ValidationExceptionMapper.ErrorResponse) response.getEntity();
        assertThat(errorResponse.message()).isEqualTo(EXPECTED_ERROR_MESSAGE);
        assertThat(errorResponse.errors()).isNotNull();
        assertThat(errorResponse.errors()).isEmpty();
    }
}
