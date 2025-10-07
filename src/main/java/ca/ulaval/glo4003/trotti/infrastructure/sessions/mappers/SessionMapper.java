package ca.ulaval.glo4003.trotti.infrastructure.sessions.mappers;

import ca.ulaval.glo4003.trotti.domain.order.values.Semester;
import ca.ulaval.glo4003.trotti.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.infrastructure.sessions.SessionRecord;

public class SessionMapper {

    public Session toDomain(SessionRecord sessionRecord) {
        String firstCharacter = sessionRecord.semesterCode().substring(0, 1);

        return new Session(Semester.fromString(firstCharacter), sessionRecord.startDate(),
                sessionRecord.endDate());
    }
}
