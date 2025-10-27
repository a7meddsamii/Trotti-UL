package ca.ulaval.glo4003.trotti.api.order.controllers;

import ca.ulaval.glo4003.trotti.api.order.dto.requests.PassListRequest;
import ca.ulaval.glo4003.trotti.api.order.dto.responses.PassListResponse;
import ca.ulaval.glo4003.trotti.api.order.mappers.PassApiMapper;
import ca.ulaval.glo4003.trotti.application.order.CartApplicationService;
import ca.ulaval.glo4003.trotti.application.order.dto.PassDto;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.authentication.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.authentication.values.AuthenticationToken;
import ca.ulaval.glo4003.trotti.domain.order.values.PassId;
import jakarta.ws.rs.core.Response;
import java.util.List;

public class CartController implements CartResource {

    private final CartApplicationService cartApplicationService;
    private final AuthenticationService authenticationService;
    private final PassApiMapper passApiMapper;

    public CartController(
            CartApplicationService cartApplicationService,
            AuthenticationService authenticationService,
            PassApiMapper passApiMapper) {
        this.cartApplicationService = cartApplicationService;
        this.authenticationService = authenticationService;
        this.passApiMapper = passApiMapper;
    }

    @Override
    public Response getCart(String tokenRequest) {
        AuthenticationToken token = AuthenticationToken.from(tokenRequest);
        Idul idul = authenticationService.authenticate(token);
        authenticationService.confirmStudent(idul);

        List<PassDto> cart = cartApplicationService.getCart(idul);

        PassListResponse passListResponse = passApiMapper.toPassListResponse(cart);

        return Response.ok(passListResponse).build();
    }

    @Override
    public Response addToCart(String tokenRequest, PassListRequest passListRequest) {
        AuthenticationToken token = AuthenticationToken.from(tokenRequest);
        Idul idul = authenticationService.authenticate(token);
        authenticationService.confirmStudent(idul);

        List<PassDto> passDtoList = passApiMapper.toPassDtoList(passListRequest);
        List<PassDto> updatedCart = cartApplicationService.addToCart(idul, passDtoList);

        PassListResponse passListResponse = passApiMapper.toPassListResponse(updatedCart);

        return Response.ok(passListResponse).build();
    }

    @Override
    public Response removeFromCart(String tokenRequest, String passId) {
        AuthenticationToken token = AuthenticationToken.from(tokenRequest);
        Idul idul = authenticationService.authenticate(token);
        authenticationService.confirmStudent(idul);

        PassId passIdToRemove = PassId.from(passId);
        cartApplicationService.removeFromCart(idul, passIdToRemove);

        return Response.noContent().build();
    }

    @Override
    public Response clearCart(String tokenRequest) {
        AuthenticationToken token = AuthenticationToken.from(tokenRequest);
        Idul idul = authenticationService.authenticate(token);
        authenticationService.confirmStudent(idul);

        cartApplicationService.clearCart(idul);

        return Response.noContent().build();
    }
}
