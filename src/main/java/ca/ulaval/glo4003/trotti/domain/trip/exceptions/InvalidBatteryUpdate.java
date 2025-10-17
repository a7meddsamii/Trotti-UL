package ca.ulaval.glo4003.trotti.domain.trip.exceptions;

public class InvalidBatteryUpdate extends RuntimeException {
    public InvalidBatteryUpdate(String message) {
        super(message);
    }
}
