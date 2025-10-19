package ca.ulaval.glo4003.trotti.domain.trip.exceptions;

public class InvalidStationException extends RuntimeException {
    public InvalidStationException(String message) {
        super(message);
    }
}
