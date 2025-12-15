package ca.ulaval.glo4003.trotti.account.fixtures;

import ca.ulaval.glo4003.trotti.account.api.dto.CreateAccountRequest;

import java.time.LocalDate;

public class CreateAccountRequestFixture {

    private static final String VALID_NAME = "Camavinga";
    private static final LocalDate VALID_BIRTHDATE = LocalDate.of(2000, 11, 10);
    private static final String VALID_GENDER = "MALE";
    private static final String VALID_IDUL = "JD12345";
    private static final String VALID_EMAIL = "john.doe@ulaval.ca";
    private static final String VALID_PASSWORD = "StrongPass1!";
    private static final String VALID_ROLE = "STUDENT";

    private String name = VALID_NAME;
    private LocalDate birthDate = VALID_BIRTHDATE;
    private String gender = VALID_GENDER;
    private String idul = VALID_IDUL;
    private String email = VALID_EMAIL;
    private String password = VALID_PASSWORD;
    private String role = VALID_ROLE;

    public CreateAccountRequest build() {
        return new CreateAccountRequest(name, birthDate, gender, idul, email, password, role);
    }
}
