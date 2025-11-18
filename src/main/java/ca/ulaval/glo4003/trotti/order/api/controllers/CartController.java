package ca.ulaval.glo4003.trotti.order.api.controllers;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.order.api.dto.requests.PassListRequest;
import ca.ulaval.glo4003.trotti.order.api.dto.responses.PassListResponse;
import ca.ulaval.glo4003.trotti.order.api.mappers.PassApiMapper;
import ca.ulaval.glo4003.trotti.order.application.CartApplicationService;
import ca.ulaval.glo4003.trotti.order.application.dto.PassDto;
import ca.ulaval.glo4003.trotti.order.domain.values.PassId;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import java.util.List;

public class CartController implements CartResource {

    private final CartApplicationService cartApplicationService;
    private final PassApiMapper passApiMapper;

    public CartController(
            CartApplicationService cartApplicationService,
            PassApiMapper passApiMapper) {
        this.cartApplicationService = cartApplicationService;
        this.passApiMapper = passApiMapper;
    }

    @Override
    public Response getCart(Idul userId) {
        List<PassDto> cart = cartApplicationService.getCart(userId);

        PassListResponse passListResponse = passApiMapper.toPassListResponse(cart);

        return Response.ok(passListResponse).build();
    }

    @Override
    public Response addToCart(Idul userId, PassListRequest passListRequest) {
        List<PassDto> passDtoList = passApiMapper.toPassDtoList(passListRequest);
        List<PassDto> updatedCart = cartApplicationService.addToCart(userId, passDtoList);

        PassListResponse passListResponse = passApiMapper.toPassListResponse(updatedCart);

        return Response.ok(passListResponse).build();
    }

    @Override
    public Response removeFromCart(Idul userId, String passId) {
        PassId passIdToRemove = PassId.from(passId);
        cartApplicationService.removeFromCart(userId, passIdToRemove);

        return Response.noContent().build();
    }

    @Override
    public Response clearCart(Idul userId) {
        cartApplicationService.clearCart(userId);

        return Response.noContent().build();
    }
}
