package ca.ulaval.glo4003.trotti.trip.api.handlers;

import ca.ulaval.glo4003.trotti.config.events.handlers.EventHandler;
import ca.ulaval.glo4003.trotti.trip.domain.events.MaintenanceRequestedEvent;
import ca.ulaval.glo4003.trotti.trip.domain.services.MaintenanceRequestNotificationService;

public class MaintenanceRequestedEventHandler implements EventHandler<MaintenanceRequestedEvent> {
    private final MaintenanceRequestNotificationService maintenanceRequestNotificationService;

    public MaintenanceRequestedEventHandler(
            MaintenanceRequestNotificationService maintenanceRequestNotificationService) {
        this.maintenanceRequestNotificationService = maintenanceRequestNotificationService;
    }

    @Override
    public void handle(MaintenanceRequestedEvent event) {
        String subject = "Maintenance Request - " + event.getLocation();
        maintenanceRequestNotificationService.notifyTechnicians(subject, event.getMessage());
    }
}
