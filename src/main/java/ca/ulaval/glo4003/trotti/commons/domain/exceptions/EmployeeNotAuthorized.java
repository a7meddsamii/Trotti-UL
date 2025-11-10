package ca.ulaval.glo4003.trotti.commons.domain.exceptions;

public class EmployeeNotAuthorized extends RuntimeException {
    public EmployeeNotAuthorized(String message) {
        super(message);
    }
}
