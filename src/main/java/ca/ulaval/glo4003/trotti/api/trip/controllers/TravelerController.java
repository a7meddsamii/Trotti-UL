package ca.ulaval.glo4003.trotti.api.trip.controllers;

import ca.ulaval.glo4003.trotti.application.trip.RidePermitActivationApplicationService;
import ca.ulaval.glo4003.trotti.application.trip.dto.RidePermitDto;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.authentication.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.authentication.values.AuthenticationToken;
import jakarta.ws.rs.core.Response;
import java.util.List;

public class TravelerController implements TravelerResource {

    private final RidePermitActivationApplicationService ridePermitActivationService;
    private final AuthenticationService authenticationService;

    public TravelerController(
            RidePermitActivationApplicationService ridePermitActivationService,
            AuthenticationService authenticationService) {
        this.ridePermitActivationService = ridePermitActivationService;
        this.authenticationService = authenticationService;
    }

    @Override
    public Response getRidePermits(String tokenRequest) {
        AuthenticationToken token = AuthenticationToken.from(tokenRequest);
        Idul idul = authenticationService.authenticate(token);

        List<RidePermitDto> ridePermits = ridePermitActivationService.getRidePermit(idul);

        return Response.ok(ridePermits).build();
    }
}
