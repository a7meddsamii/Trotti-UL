package ca.ulaval.glo4003.trotti.infrastructure.sessions.mappers;

import ca.ulaval.glo4003.trotti.domain.order.Session;
import ca.ulaval.glo4003.trotti.domain.order.values.Semester;
import ca.ulaval.glo4003.trotti.infrastructure.sessions.repository.SessionRecord;

public class SessionMapper {

    public Session toDomain(SessionRecord dto) {
        String firstCharacter = dto.semesterCode().substring(0, 1);

        return new Session(Semester.fromString(firstCharacter), dto.startDate(), dto.endDate());
    }
}
