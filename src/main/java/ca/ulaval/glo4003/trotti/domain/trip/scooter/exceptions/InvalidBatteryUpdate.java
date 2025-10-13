package ca.ulaval.glo4003.trotti.domain.trip.scooter.exceptions;

public class InvalidBatteryUpdate extends RuntimeException {
    public InvalidBatteryUpdate(String message) {
        super(message);
    }
}
