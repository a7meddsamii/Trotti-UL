package ca.ulaval.glo4003.trotti.authentication.api_temp;

import ca.ulaval.glo4003.trotti.account.api.dto.LoginRequest;
import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.authentication.application.AuthenticationService;
import ca.ulaval.glo4003.trotti.authentication.application.dto.LoginInfo;
import ca.ulaval.glo4003.trotti.authentication.domain.values.Permission;
import ca.ulaval.glo4003.trotti.authentication.domain.values.SessionToken;
import ca.ulaval.glo4003.trotti.authentication.infrastructure.security.authorization.RequiresPermissions;
import ca.ulaval.glo4003.trotti.commons.domain.Email;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;

@Path("/auth-test")
public class TempController {
	public static final Logger LOGGER =
			org.slf4j.LoggerFactory.getLogger(TempController.class);
	
	private final AuthenticationService authenticationService;
	
	@Inject
	Idul userId;
	
	public TempController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}
	
	@POST
	@PermitAll
	@Path("/login")
	public Response login(@Valid LoginRequest request) {
		LoginInfo info = new LoginInfo(Email.from(request.email()), request.password());
		SessionToken token = authenticationService.login(info);
		
		return Response.ok(token.toString()).build();
	}
	
	@POST
	@Path("/logout")
	@RequiresPermissions({Permission.CART_MODIFICATION})
	public Response logout() {
		LOGGER.info("################ TEESSSSSTTTT WORRKKSSS request from User ID {} ####################", userId);
		
		return Response.ok().entity("\"Test request from User ID " + userId + " worked\"").build();
	}
}
