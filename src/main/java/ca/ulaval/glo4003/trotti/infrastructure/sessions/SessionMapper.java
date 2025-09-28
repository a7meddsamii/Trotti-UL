package ca.ulaval.glo4003.trotti.infrastructure.sessions;

import ca.ulaval.glo4003.trotti.domain.order.Session;

public class SessionMapper {
    public Session toDomain(SessionDTO dto) {
        return new Session(dto.semesterCode(), dto.startDate(), dto.endDate());
    }
}
