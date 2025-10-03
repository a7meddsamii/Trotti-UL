package ca.ulaval.glo4003.trotti.api;

import ca.ulaval.glo4003.trotti.api.dto.requests.PassListRequest;
import ca.ulaval.glo4003.trotti.application.order.dto.PassDto;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.domain.order.Session;
import ca.ulaval.glo4003.trotti.domain.order.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.domain.order.values.MaximumDailyTravelTime;
import ca.ulaval.glo4003.trotti.infrastructure.config.providers.SessionProvider;

import java.time.Duration;
import java.util.List;

public class PassApiMapper {

    public List<PassDto> toPassDtoList(PassListRequest request) {
        return request.passes().stream()
                .map(this::toPassDto)
                .toList();
    }

    private PassDto toPassDto(PassListRequest.PassRequest request) {
        return new PassDto(
                MaximumDailyTravelTime.from(Duration.ofMinutes(request.maximumDailyTravelTime())),
                sessionFromString(request.session()),
                BillingFrequency.fromString(request.billingFrequency()),
                null
        );
    }

    private Session sessionFromString(String sessionRequest) {
        List<Session> sessionList = SessionProvider.getInstance().getSessions();
        for (Session session : sessionList) {
            if (sessionRequest.equals(session.toString())) {
                return session;
            }
        }

        throw new InvalidParameterException("Session not found.");
    }
}
