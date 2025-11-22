package ca.ulaval.glo4003.trotti.billing.domain.ridepermit.exceptions;

public class InvalidRidePermitOperation extends RuntimeException {
    public InvalidRidePermitOperation(String message) {
        super(message);
    }
}
