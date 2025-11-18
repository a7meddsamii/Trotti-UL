package ca.ulaval.glo4003.trotti.trip.api.controllers;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.api.dto.responses.UnlockCodeResponse;
import ca.ulaval.glo4003.trotti.trip.application.UnlockCodeApplicationService;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

public class UnlockCodeController implements UnlockCodeResource {

    private final UnlockCodeApplicationService unlockCodeApplicationService;
    @Inject
    private Idul userId;

    public UnlockCodeController(UnlockCodeApplicationService unlockCodeApplicationService) {
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
