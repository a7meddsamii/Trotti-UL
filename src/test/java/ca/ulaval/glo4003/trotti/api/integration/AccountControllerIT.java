package ca.ulaval.glo4003.trotti.api.integration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import ca.ulaval.glo4003.trotti.api.account.dto.request.CreateAccountRequest;
import ca.ulaval.glo4003.trotti.domain.account.fixture.AccountFixture;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

public class AccountControllerIT {
    private static final String APPLICATION_JSON = "application/json";
    private static final String ACCOUNTS_ENDPOINT = "/api/accounts";

    @Test
    void whenPostValidAccount_thenReturn201() {
        CreateAccountRequest request =
                new CreateAccountRequest(AccountFixture.A_NAME, AccountFixture.A_BIRTHDATE,
                        AccountFixture.A_GENDER_STRING, AccountFixture.AN_IDUL_STRING,
                        AccountFixture.AN_EMAIL_STRING, AccountFixture.A_RAW_PASSWORD);

        given().contentType(APPLICATION_JSON).body(request).when().post(ACCOUNTS_ENDPOINT).then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .header(HttpHeaders.LOCATION, equalTo(ACCOUNTS_ENDPOINT + request.idul()));
    }
}
