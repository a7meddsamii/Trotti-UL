package ca.ulaval.glo4003.trotti.trip.domain.exceptions;

public class InvalidBatteryValue extends RuntimeException {
    public InvalidBatteryValue(String message) {
        super(message);
    }
}
