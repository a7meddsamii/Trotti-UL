package ca.ulaval.glo4003.trotti.application.order.dto;

import java.util.List;

public record OrderDto(
        List<PassDto> passList
) {}
