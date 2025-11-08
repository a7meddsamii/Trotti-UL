package ca.ulaval.glo4003.trotti.authentication.domain.entities;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.authentication.domain.exception.AuthenticationException;
import ca.ulaval.glo4003.trotti.authentication.domain.values.Permission;
import ca.ulaval.glo4003.trotti.authentication.domain.values.Role;
import ca.ulaval.glo4003.trotti.commons.domain.Password;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
class IdentityTest {
	private static final Idul AN_IDUL = Idul.from("JD12345");
	private static final Role A_ROLE = Role.STUDENT;
	private static final Set<Permission> PERMISSIONS =
			Set.of(Permission.REQUEST_MAINTENANCE, Permission.END_MAINTENANCE);
	private static final String A_RAW_PASSWORD = "StrongPass1!";
	private static final String MATCHING_RAW_PASSWORD = "MatchingPass1!";
	private static final String NON_MATCHING_RAW_PASSWORD = "NonMatchingPass4!";
	private Password password;
	
	private Identity identity;
	
	@BeforeEach
	void setup() {
		password = Mockito.mock(Password.class);
		identity = new Identity(AN_IDUL, A_ROLE, PERMISSIONS, password);
	}
	
	@Test
	void givenPassword_whenVerifyPassword_thenPasswordMatchesIsCalled() {
		Mockito.when(password.matches(A_RAW_PASSWORD)).thenReturn(true);
		
		identity.verifyPassword(A_RAW_PASSWORD);
		
		Mockito.verify(password).matches(A_RAW_PASSWORD);
	}
	
	@Test
	void givenMatchingPassword_whenVerifyPassword_thenDoesNotThrowsAuthenticationException() {
		Mockito.when(password.matches(MATCHING_RAW_PASSWORD)).thenReturn(true);
		
		Executable executable = () -> identity.verifyPassword(MATCHING_RAW_PASSWORD);
		
		Assertions.assertDoesNotThrow(executable);
	}
	
	@Test
	void givenNonMatchingPassword_whenVerifyPassword_thenThrowsAuthenticationException() {
		Mockito.when(password.matches(NON_MATCHING_RAW_PASSWORD)).thenReturn(false);
		
		Executable executable = () -> identity.verifyPassword(NON_MATCHING_RAW_PASSWORD);
		
		Assertions.assertThrows(AuthenticationException.class, executable);
	}
}
