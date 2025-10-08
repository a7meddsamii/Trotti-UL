package ca.ulaval.glo4003.trotti.api.order.controllers;

import ca.ulaval.glo4003.trotti.api.order.dto.requests.PassListRequest;
import ca.ulaval.glo4003.trotti.api.order.dto.responses.PassListResponse;
import ca.ulaval.glo4003.trotti.api.order.mappers.PassApiMapper;
import ca.ulaval.glo4003.trotti.application.order.CartApplicationService;
import ca.ulaval.glo4003.trotti.application.order.dto.PassDto;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.authentication.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

class CartResourceTest {

    private static final String AUTH_HEADER = "Bearer test.jwt.token";
    private static final String PASS_ID = Id.randomId().toString();

    private CartApplicationService cartApplicationService;
    private AuthenticationService authenticationService;
    private PassApiMapper passApiMapper;

    private CartResource resource;

    @BeforeEach
    void setup() {
        cartApplicationService = Mockito.mock(CartApplicationService.class);
        authenticationService = Mockito.mock(AuthenticationService.class);
        passApiMapper = Mockito.mock(PassApiMapper.class);

        resource = new CartResource(cartApplicationService, authenticationService, passApiMapper);
    }

    private List<PassDto> passDtos() {
        return List.of(Mockito.mock(PassDto.class));
    }

    private PassListResponse passListResponse() {
        return Mockito.mock(PassListResponse.class);
    }

    @Test
    void givenValidToken_whenGetCart_thenReturns200AndBody() {
        Idul idul = Mockito.mock(Idul.class);
        List<PassDto> cartDtos = passDtos();
        PassListResponse expectedResponse = passListResponse();

        Mockito.when(authenticationService.authenticate(ArgumentMatchers.any())).thenReturn(idul);
        Mockito.when(cartApplicationService.getCart(idul)).thenReturn(cartDtos);
        Mockito.when(passApiMapper.toPassListResponse(cartDtos)).thenReturn(expectedResponse);

        Response response = resource.getCart(AUTH_HEADER);

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(expectedResponse, response.getEntity());

        Mockito.verify(authenticationService).authenticate(ArgumentMatchers.any());
        Mockito.verify(cartApplicationService).getCart(idul);
        Mockito.verify(passApiMapper).toPassListResponse(cartDtos);
    }

    @Test
    void givenValidRequest_whenAddToCart_thenReturns200AndBody() {
        Idul idul = Mockito.mock(Idul.class);
        PassListRequest request = Mockito.mock(PassListRequest.class);
        List<PassDto> toAdd = passDtos();
        List<PassDto> updated = passDtos();
        PassListResponse mapped = passListResponse();

        Mockito.when(authenticationService.authenticate(ArgumentMatchers.any())).thenReturn(idul);
        Mockito.when(passApiMapper.toPassDtoList(request)).thenReturn(toAdd);
        Mockito.when(cartApplicationService.addToCart(idul, toAdd)).thenReturn(updated);
        Mockito.when(passApiMapper.toPassListResponse(updated)).thenReturn(mapped);

        Response response = resource.addToCart(AUTH_HEADER, request);

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(mapped, response.getEntity());

        Mockito.verify(authenticationService).authenticate(ArgumentMatchers.any());
        Mockito.verify(passApiMapper).toPassDtoList(request);
        Mockito.verify(cartApplicationService).addToCart(idul, toAdd);
        Mockito.verify(passApiMapper).toPassListResponse(updated);
    }

    @Test
    void givenPassId_whenRemoveFromCart_thenReturns204() {
        Idul idul = Mockito.mock(Idul.class);
        Mockito.when(authenticationService.authenticate(ArgumentMatchers.any())).thenReturn(idul);

        Response response = resource.removeFromCart(AUTH_HEADER, PASS_ID);

        Assertions.assertEquals(204, response.getStatus());
        Assertions.assertNull(response.getEntity());

        Mockito.verify(authenticationService).authenticate(ArgumentMatchers.any());
        Mockito.verify(cartApplicationService).removeFromCart(Mockito.eq(idul),
                Mockito.any(Id.class));
        Mockito.verifyNoInteractions(passApiMapper);
    }

    @Test
    void whenClearCart_thenReturns204() {
        Idul idul = Mockito.mock(Idul.class);
        Mockito.when(authenticationService.authenticate(ArgumentMatchers.any())).thenReturn(idul);

        Response response = resource.clearCart(AUTH_HEADER);

        Assertions.assertEquals(204, response.getStatus());
        Assertions.assertNull(response.getEntity());

        Mockito.verify(authenticationService).authenticate(ArgumentMatchers.any());
        Mockito.verify(cartApplicationService).clearCart(idul);
        Mockito.verifyNoInteractions(passApiMapper);
    }
}
