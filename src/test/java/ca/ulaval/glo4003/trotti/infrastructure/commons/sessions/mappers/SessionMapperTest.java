package ca.ulaval.glo4003.trotti.infrastructure.commons.sessions.mappers;

import ca.ulaval.glo4003.trotti.commons.infrastructure.config.providers.sessions.SessionMapper;
import ca.ulaval.glo4003.trotti.order.domain.values.Semester;
import ca.ulaval.glo4003.trotti.order.domain.values.Session;
import ca.ulaval.glo4003.trotti.commons.infrastructure.config.providers.sessions.SessionRecord;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SessionMapperTest {
    private static final String A_SEMESTER_CODE = "A2023";
    private static final LocalDate A_START_DATE = LocalDate.of(2025, Month.SEPTEMBER, 1);
    private static final LocalDate AN_END_DATE = LocalDate.of(2025, Month.DECEMBER, 31);

    private SessionMapper sessionMapper;

    @BeforeEach
    void setup() {
        sessionMapper = new SessionMapper();
    }

    @Test
    void givenSessionRecord_whenToDomain_thenReturnsCorrectSession() {
        SessionRecord sessionRecord = new SessionRecord(A_SEMESTER_CODE, A_START_DATE, AN_END_DATE);

        Session session = sessionMapper.toDomain(sessionRecord);

        Assertions.assertEquals(Semester.FALL, session.getSemester());
        Assertions.assertEquals(A_START_DATE, session.getStartDate());
        Assertions.assertEquals(AN_END_DATE, session.getEndDate());
    }
}
