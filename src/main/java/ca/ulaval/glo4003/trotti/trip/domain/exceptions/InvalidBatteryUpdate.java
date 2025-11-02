package ca.ulaval.glo4003.trotti.trip.domain.exceptions;

public class InvalidBatteryUpdate extends RuntimeException {
    public InvalidBatteryUpdate(String message) {
        super(message);
    }
}
