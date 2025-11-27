package ca.ulaval.glo4003.trotti.fleet.domain.exceptions;

public class InvalidBatteryUpdate extends RuntimeException {
    public InvalidBatteryUpdate(String message) {
        super(message);
    }
}
