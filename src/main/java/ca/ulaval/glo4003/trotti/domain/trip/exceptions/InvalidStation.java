package ca.ulaval.glo4003.trotti.domain.trip.exceptions;

public class InvalidStation extends RuntimeException {
    public InvalidStation(String message) {
        super(message);
    }
}
