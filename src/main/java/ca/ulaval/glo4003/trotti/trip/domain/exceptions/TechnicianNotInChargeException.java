package ca.ulaval.glo4003.trotti.trip.domain.exceptions;

public class TechnicianNotInChargeException extends RuntimeException {
    public TechnicianNotInChargeException(String message) {
        super(message);
    }
}
