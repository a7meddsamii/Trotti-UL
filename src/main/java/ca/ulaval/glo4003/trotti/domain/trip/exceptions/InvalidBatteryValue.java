package ca.ulaval.glo4003.trotti.domain.trip.exceptions;

public class InvalidBatteryValue extends RuntimeException {
    public InvalidBatteryValue(String message) {
        super(message);
    }
}
