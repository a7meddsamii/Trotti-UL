package ca.ulaval.glo4003.trotti.commons.infrastructure.config.providers.sessions;

import ca.ulaval.glo4003.trotti.commons.domain.values.session.Semester;
import ca.ulaval.glo4003.trotti.commons.domain.values.session.Session;

public class SessionMapper {

    public Session toDomain(SessionRecord sessionRecord) {
        String firstCharacter = sessionRecord.semesterCode().substring(0, 1);

        return new Session(Semester.fromString(firstCharacter), sessionRecord.startDate(),
                sessionRecord.endDate());
    }
}
