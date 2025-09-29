package ca.ulaval.glo4003.trotti.infrastructure.sessions.mappers;

import ca.ulaval.glo4003.trotti.domain.order.Session;
import ca.ulaval.glo4003.trotti.domain.order.values.Semester;
import ca.ulaval.glo4003.trotti.infrastructure.sessions.repository.SessionRecord;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SessionMapperTest {
    private static final String A_SEMESTER_CODE = "A2023";
    private static final String A_START_DATE = "2023-09-01";
    private static final String AN_END_DATE = "2023-12-15";

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
        Assertions.assertEquals(LocalDate.parse(A_START_DATE), session.getStartDate());
        Assertions.assertEquals(LocalDate.parse(AN_END_DATE), session.getEndDate());
    }
}
