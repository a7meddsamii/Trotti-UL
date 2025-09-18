package ca.ulaval.glo4003.trotti.api.exceptionhandling;

import ca.ulaval.glo4003.trotti.domain.shared.exception.ErrorType;

public record ApiErrorResponse(ErrorType errorType, String message) {}
