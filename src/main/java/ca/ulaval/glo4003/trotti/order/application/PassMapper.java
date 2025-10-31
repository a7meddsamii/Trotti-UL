package ca.ulaval.glo4003.trotti.order.application;

import ca.ulaval.glo4003.trotti.order.application.dto.PassDto;
import ca.ulaval.glo4003.trotti.order.domain.entities.pass.Pass;

public class PassMapper {

    public PassDto toDto(Pass pass) {
        return new PassDto(pass.getMaximumTravelingTime(), pass.getSession(),
                pass.getBillingFrequency(), pass.getId());
    }
}
