package ca.ulaval.glo4003.trotti.domain.pass;

import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;

public enum Semester {
    FALL("Automne"), WINTER("Hiver"), SUMMER("Été");

    private final String frenchTranslation;

    Semester(String frenchVersion) {
        this.frenchTranslation = frenchVersion;
    }

    public static Semester fromString(String semesterString) {
        for (Semester semester : Semester.values()) {
            if (semester.name().equalsIgnoreCase(semesterString)
                    || semester.frenchTranslation.equalsIgnoreCase(semesterString)) {
                return semester;
            }
        }
        throw new InvalidParameterException("Invalid semester: " + semesterString);
    }

    public String getFrenchTranslation() {
        return frenchTranslation;
    }
}
