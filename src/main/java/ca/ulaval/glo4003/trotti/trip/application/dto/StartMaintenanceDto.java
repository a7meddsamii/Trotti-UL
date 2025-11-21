package ca.ulaval.glo4003.trotti.trip.application.dto;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;

public record StartMaintenanceDto(
        Location location,
        Idul technicianId
) {}
