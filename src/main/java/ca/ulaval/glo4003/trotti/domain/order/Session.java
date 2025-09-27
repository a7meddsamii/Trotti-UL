package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;
import java.time.LocalDate;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

public class Session {
    private static final String CODE_PATTERN = "^[AEH]\\d{2}$";
    private final String code;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Session(String code, LocalDate startDate, LocalDate endDate) {
        validateCode(code);
        this.code = code;

        validateDates(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getCode() {
        return code;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    private void validateCode(String code) {

        if (StringUtils.isBlank(code)) {
            throw new InvalidParameterException(
                    "Session code is missing, it cannot be null or empty");
        }

        if (!code.matches(CODE_PATTERN)) {
            throw new InvalidParameterException("Session code is invalid");
        }
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {

        if (startDate == null) {
            throw new InvalidParameterException("Session startDate is null");
        }

        if (endDate == null) {
            throw new InvalidParameterException("Session endDate is null");
        }

        if (!endDate.isAfter(startDate)) {
            throw new InvalidParameterException("Session dates are invalid");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Session session = (Session) o;
        return code.equals(session.getCode()) && startDate.equals(session.getStartDate())
                && endDate.equals(session.getEndDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, startDate, endDate);
    }

    public boolean includes(LocalDate date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
}
