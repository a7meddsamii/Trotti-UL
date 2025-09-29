package ca.ulaval.glo4003.trotti.infrastructure.sessions.mappers;

import ca.ulaval.glo4003.trotti.domain.order.Session;
import ca.ulaval.glo4003.trotti.domain.order.values.Semester;
import ca.ulaval.glo4003.trotti.infrastructure.sessions.repository.SessionRecord;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SessionMapperTest {
    private static final String A_SEMESTER_CODE = "A2023";
    private static final LocalDate A_START_DATE = LocalDate.of(2025, Month.SEPTEMBER, 1);
    private static final LocalDate AN_END_DATE = LocalDate.of(2025, Month.DECEMBER, 31);

    private SessionRecord sessionRecord;

    private SessionMapper sessionMapper;

    @BeforeEach
    void setUp() {
        sessionRecord = Mockito.mock(SessionRecord.class);
        sessionMapper = new SessionMapper();
    }

    @Test
    void givenSessionRecord_whenToDomain_thenReturnsCorrectSession() {
        Mockito.when(sessionRecord.semesterCode()).thenReturn(A_SEMESTER_CODE);
        Mockito.when(sessionRecord.startDate()).thenReturn(A_START_DATE);
        Mockito.when(sessionRecord.endDate()).thenReturn(AN_END_DATE);

        Session session = sessionMapper.toDomain(sessionRecord);

        Assertions.assertEquals(Semester.FALL, session.getSemester());
        Assertions.assertEquals(A_START_DATE, session.getStartDate());
        Assertions.assertEquals(AN_END_DATE, session.getEndDate());
    }
}
