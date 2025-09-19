package ca.ulaval.glo4003.trotti.api.exceptionhandling;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.trotti.domain.shared.exception.ErrorType;
import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApiExceptionHandlerTest {
  private static final String ERROR_MESSAGE = "Invalid parameter";
  private static final ErrorType ERROR_TYPE = ErrorType.INVALID_EMAIL;
  private static final String EXPECTED_CONTENT_TYPE = "application/json";
  private static final int EXPECTED_STATUS = Response.Status.BAD_REQUEST.getStatusCode();
  private static final String CONTENT_TYPE_HEADER = "Content-Type";

  private static final InvalidParameterException EXCEPTION = new InvalidParameterException(
    ERROR_TYPE,
    ERROR_MESSAGE
  );

  private ApiExceptionHandler handler;
  private Response response;
  private ApiErrorResponse errorResponse;

  @BeforeEach
  void setup() {
    handler = new ApiExceptionHandler();
    response = handler.toResponse(EXCEPTION);
    errorResponse = (ApiErrorResponse) response.getEntity();
  }

  @Test
  void givenInvalidParameterException_whenToResponse_thenReturnBadRequestStatus() {
    assertEquals(EXPECTED_STATUS, response.getStatus());
  }

  @Test
  void givenInvalidParameterException_whenToResponse_thenReturnJsonContentType() {
    assertEquals(
      EXPECTED_CONTENT_TYPE,
      response.getHeaderString(CONTENT_TYPE_HEADER)
    );
  }

  @Test
  void givenInvalidParameterException_whenToResponse_thenReturnErrorResponseWithExpectedType() {
    assertEquals(ERROR_TYPE, errorResponse.errorType());
  }

  @Test
  void givenInvalidParameterException_whenToResponse_thenReturnErrorResponseWithExpectedMessage() {
    assertEquals(ERROR_MESSAGE, errorResponse.message());
  }
}
