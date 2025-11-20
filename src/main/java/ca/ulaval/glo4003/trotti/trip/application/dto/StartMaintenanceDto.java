package ca.ulaval.glo4003.trotti.trip.application.dto;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;

public record StartMaintenanceDto(
        Location location,
        Idul technicianId
) {}
