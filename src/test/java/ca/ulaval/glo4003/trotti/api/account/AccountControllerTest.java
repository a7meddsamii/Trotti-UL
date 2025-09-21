// src/test/java/ca/ulaval/glo4003/trotti/api/account/AccountAuthControllerTest.java
package ca.ulaval.glo4003.trotti.api.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.trotti.api.account.dto.request.LoginRequest;
import ca.ulaval.glo4003.trotti.api.account.dto.response.LoginResponse;
import ca.ulaval.glo4003.trotti.application.account.AccountService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    private static final String EMAIL = "equipe10@example.com";
    private static final String PASSWORD = "Équipedix2025$";
    private static final String TOKEN = "jwt-token-value";

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController controller;

    @Test
    void whenLogin_ok_returns200WithBody() {
        LoginRequest request = new LoginRequest(EMAIL, PASSWORD);
        when(accountService.login(EMAIL, PASSWORD)).thenReturn(TOKEN);

        Response response = controller.login(request);

        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(response.getEntity()).isInstanceOf(LoginResponse.class);

        LoginResponse body = (LoginResponse) response.getEntity();
        assertThat(body.token()).isEqualTo(TOKEN);

        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> pwdCaptor = ArgumentCaptor.forClass(String.class);
        verify(accountService).login(emailCaptor.capture(), pwdCaptor.capture());
        assertThat(emailCaptor.getValue()).isEqualTo(EMAIL);
        assertThat(pwdCaptor.getValue()).isEqualTo(PASSWORD);
    }
}
