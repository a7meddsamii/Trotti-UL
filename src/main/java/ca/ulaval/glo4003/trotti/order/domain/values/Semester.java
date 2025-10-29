package ca.ulaval.glo4003.trotti.order.domain.values;

import ca.ulaval.glo4003.trotti.commons.exceptions.InvalidParameterException;

public enum Semester {
    FALL("Automne", 'A'), WINTER("Hiver", 'H'), SUMMER("Été", 'E');

    private final String frenchTranslation;
    private final Character code;

    Semester(String frenchVersion, Character code) {
        this.frenchTranslation = frenchVersion;
        this.code = code;
    }

    public static Semester fromString(String semesterString) {
        for (Semester semester : Semester.values()) {
            if (semester.name().equalsIgnoreCase(semesterString)
                    || semester.frenchTranslation.equalsIgnoreCase(semesterString)
                    || semester.code.toString().equalsIgnoreCase(semesterString)) {
                return semester;
            }
        }
        throw new InvalidParameterException("Invalid semester: " + semesterString);
    }

    public Character getCode() {
        return code;
    }

    public String getFrenchTranslation() {
        return frenchTranslation;
    }
}
