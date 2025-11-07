package ca.ulaval.glo4003.trotti.trip.api.controllers;

import ca.ulaval.glo4003.trotti.account.domain.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.account.domain.values.AuthenticationToken;
import ca.ulaval.glo4003.trotti.commons.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.api.dto.responses.UnlockCodeResponse;
import ca.ulaval.glo4003.trotti.trip.application.UnlockCodeApplicationService;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
import jakarta.ws.rs.core.Response;

public class UnlockCodeController implements UnlockCodeResource {

    private final AuthenticationService authenticationService;
    private final UnlockCodeApplicationService unlockCodeApplicationService;

    public UnlockCodeController(
            AuthenticationService authenticationService,
            UnlockCodeApplicationService unlockCodeApplicationService) {
        this.authenticationService = authenticationService;
        this.unlockCodeApplicationService = unlockCodeApplicationService;
    }

    @Override
    public Response requestUnlockCode(String tokenRequest, String ridePermitId) {
        AuthenticationToken token = AuthenticationToken.from(tokenRequest);
        Idul idul = authenticationService.authenticate(token);

        unlockCodeApplicationService.generateUnlockCode(idul, RidePermitId.from(ridePermitId));

        return Response.ok().entity(
                new UnlockCodeResponse("Unlock Code is generated successfully and sent by e-mail."))
                .build();
    }
}
