package ca.ulaval.glo4003.trotti.billing.application.order.dto;

import ca.ulaval.glo4003.trotti.account.domain.values.Role;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;

public record FreeRidePermitItemGrantDto(Idul riderId, Role role) {
}
