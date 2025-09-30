package ca.ulaval.glo4003.trotti.application.order.mappers;

import ca.ulaval.glo4003.trotti.application.order.dto.PassRequestDto;
import ca.ulaval.glo4003.trotti.application.order.dto.PassResponseDto;
import ca.ulaval.glo4003.trotti.domain.order.Pass;
import ca.ulaval.glo4003.trotti.domain.order.PassFactory;
import java.util.ArrayList;
import java.util.List;

public class PassMapper {
    private final PassFactory passFactory;

    public PassMapper(PassFactory passFactory) {
        this.passFactory = passFactory;
    }

    public Pass toDomain(PassRequestDto passDto) {
        return passFactory.create(passDto.maximumDailyTravelTime(), passDto.session(),
                passDto.billingFrequency());
    }

    public List<Pass> toDomain(List<PassRequestDto> passDtoList) {
        List<Pass> passList = new ArrayList<>();
        for (PassRequestDto passDto : passDtoList) {
            passList.add(toDomain(passDto));
        }

        return passList;
    }

    public PassResponseDto toDto(Pass pass) {
        return new PassResponseDto(pass.getMaximumTravelingTime(), pass.getSession(),
                pass.getBillingFrequency(), pass.getId());
    }

    public List<PassResponseDto> toDto(List<Pass> passList) {
        List<PassResponseDto> passDtoList = new ArrayList<>();
        for (Pass pass : passList) {
            passDtoList.add(toDto(pass));
        }

        return passDtoList;
    }
}
