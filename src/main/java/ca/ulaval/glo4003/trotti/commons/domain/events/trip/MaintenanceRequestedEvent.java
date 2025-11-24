package ca.ulaval.glo4003.trotti.commons.domain.events.trip;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.Event;

public class MaintenanceRequestedEvent extends Event {

    private final String location;
    private final String message;

    public MaintenanceRequestedEvent(Idul idul, String location, String message) {
        super(idul, "maintenance.requested");
        this.location = location;
        this.message = message;
    }

    public String getLocation() {
        return location;
    }

    public String getMessage() {
        return message;
    }
}
