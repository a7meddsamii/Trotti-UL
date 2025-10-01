package ca.ulaval.glo4003.trotti.api.exceptionmappers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ConstraintViolationExceptionMapperTest {

    private static final String FIRST_ERROR_MESSAGE = "Name is required";
    private static final String SECOND_ERROR_MESSAGE = "Email is invalid";
    private static final String EXPECTED_COMBINED_MESSAGE =
            FIRST_ERROR_MESSAGE + ", " + SECOND_ERROR_MESSAGE;
    private static final int BAD_REQUEST_STATUS_CODE = Response.Status.BAD_REQUEST.getStatusCode();

    private ConstraintViolationExceptionMapper constraintViolationExceptionMapper;

    @BeforeEach
    void setup() {
        constraintViolationExceptionMapper = new ConstraintViolationExceptionMapper();
    }

    @Test
    void givenConstraintViolations_whenToResponse_thenStatusIsBadRequest() {
        ConstraintViolationException exception = buildConstraintViolationException();

        Response response = constraintViolationExceptionMapper.toResponse(exception);

        Assertions.assertEquals(BAD_REQUEST_STATUS_CODE, response.getStatus());
    }

    @Test
    void givenConstraintViolations_whenToResponse_thenEntityContainsCombinedMessages() {
        ConstraintViolationException exception = buildConstraintViolationException();

        Response response = constraintViolationExceptionMapper.toResponse(exception);
        ApiErrorResponse errorResponse = (ApiErrorResponse) response.getEntity();

        Assertions.assertEquals(EXPECTED_COMBINED_MESSAGE, errorResponse.message());
    }

    private ConstraintViolationException buildConstraintViolationException() {
        ConstraintViolation<?> violation1 = Mockito.mock(ConstraintViolation.class);
        Mockito.when(violation1.getMessage()).thenReturn(FIRST_ERROR_MESSAGE);

        ConstraintViolation<?> violation2 = Mockito.mock(ConstraintViolation.class);
        Mockito.when(violation2.getMessage()).thenReturn(SECOND_ERROR_MESSAGE);

        Set<ConstraintViolation<?>> violations = new LinkedHashSet<>();
        violations.add(violation1);
        violations.add(violation2);

        return new ConstraintViolationException(violations);
    }
}
