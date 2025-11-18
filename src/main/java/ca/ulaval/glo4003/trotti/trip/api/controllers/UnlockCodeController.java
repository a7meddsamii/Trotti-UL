package ca.ulaval.glo4003.trotti.trip.api.controllers;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.api.dto.responses.UnlockCodeResponse;
import ca.ulaval.glo4003.trotti.trip.application.UnlockCodeApplicationService;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
import jakarta.ws.rs.core.Response;

public class UnlockCodeController implements UnlockCodeResource {

    private final UnlockCodeApplicationService unlockCodeApplicationService;

    public UnlockCodeController(UnlockCodeApplicationService unlockCodeApplicationService) {
        this.unlockCodeApplicationService = unlockCodeApplicationService;
    }

    @Override
    public Response requestUnlockCode(Idul userId, String ridePermitId) {
        unlockCodeApplicationService.generateUnlockCode(userId, RidePermitId.from(ridePermitId));

        return Response.ok().entity(
                new UnlockCodeResponse("Unlock Code is generated successfully and sent by e-mail."))
                .build();
    }
}
