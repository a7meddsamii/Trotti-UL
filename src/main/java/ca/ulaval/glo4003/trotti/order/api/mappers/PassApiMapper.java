package ca.ulaval.glo4003.trotti.order.api.mappers;

import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.commons.infrastructure.config.providers.sessions.SessionProvider;
import ca.ulaval.glo4003.trotti.order.api.dto.requests.PassListRequest;
import ca.ulaval.glo4003.trotti.order.api.dto.responses.PassListResponse;
import ca.ulaval.glo4003.trotti.order.application.dto.PassDto;
import ca.ulaval.glo4003.trotti.order.domain.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.order.domain.values.MaximumDailyTravelTime;
import ca.ulaval.glo4003.trotti.commons.domain.values.session.Session;
import ca.ulaval.glo4003.trotti.payment.domain.values.money.Money;
import java.time.Duration;
import java.util.List;

public class PassApiMapper {
    private final SessionProvider sessionProvider;

    public PassApiMapper(SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

    public List<PassDto> toPassDtoList(PassListRequest request) {
        if (request == null) {
            throw new InvalidParameterException("Provide the passes information");
        }

        return request.passes().stream().map(this::toPassDto).toList();
    }

    public PassListResponse toPassListResponse(List<PassDto> passDtoList) {
        List<PassListResponse.PassResponse> passResponses = passDtoList.stream()
                .map(passDto -> new PassListResponse.PassResponse(passDto.id().toString(),
                        passDto.maximumDailyTravelTime().toString(), passDto.session().toString(),
                        passDto.billingFrequency().toString(),
                        passDto.maximumDailyTravelTime().calculateAmount().toString()))
                .toList();

        Money totalCost = Money.zeroCad();
        for (PassDto passDto : passDtoList) {
            totalCost = totalCost.plus(passDto.maximumDailyTravelTime().calculateAmount());
        }

        return new PassListResponse(passResponses, totalCost.toString());
    }

    private PassDto toPassDto(PassListRequest.PassRequest request) {
        return new PassDto(
                MaximumDailyTravelTime.from(Duration.ofMinutes(request.maximumDailyTravelTime())),
                sessionFromString(request.session()),
                BillingFrequency.fromString(request.billingFrequency()), null);
    }

    private Session sessionFromString(String sessionRequest) {
        List<Session> sessionList = sessionProvider.getSessions();
        for (Session session : sessionList) {
            if (sessionRequest.equals(session.toString())) {
                return session;
            }
        }

        throw new InvalidParameterException("Session not found.");
    }
}
