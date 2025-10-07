package ca.ulaval.glo4003.trotti.application.order.mappers;

import ca.ulaval.glo4003.trotti.application.order.dto.PassDto;
import ca.ulaval.glo4003.trotti.domain.order.entities.pass.Pass;

public class PassMapper {

    public PassDto toDto(Pass pass) {
        return new PassDto(pass.getMaximumTravelingTime(), pass.getSession(),
                pass.getBillingFrequency(), pass.getId());
    }
}
