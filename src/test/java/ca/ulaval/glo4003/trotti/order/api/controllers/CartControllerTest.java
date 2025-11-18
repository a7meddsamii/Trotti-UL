package ca.ulaval.glo4003.trotti.order.api.controllers;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.order.api.dto.requests.PassListRequest;
import ca.ulaval.glo4003.trotti.order.api.dto.responses.PassListResponse;
import ca.ulaval.glo4003.trotti.order.api.mappers.PassApiMapper;
import ca.ulaval.glo4003.trotti.order.application.CartApplicationService;
import ca.ulaval.glo4003.trotti.order.application.dto.PassDto;
import ca.ulaval.glo4003.trotti.order.domain.values.PassId;
import jakarta.ws.rs.core.Response;
import java.lang.reflect.Field;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CartControllerTest {

    private static final String PASS_ID = PassId.randomId().toString();

    private CartApplicationService cartApplicationService;
    private PassApiMapper passApiMapper;

    private CartResource resource;

    private Idul idul;

    @BeforeEach
    void setup() {
        idul = Mockito.mock(Idul.class);
        cartApplicationService = Mockito.mock(CartApplicationService.class);
        passApiMapper = Mockito.mock(PassApiMapper.class);

        resource = new CartController(cartApplicationService, passApiMapper);
        idul = Mockito.mock(Idul.class);
    }

    @Test
    void givenValidToken_whenGetCart_thenReturns200AndBody() {
        List<PassDto> cartDtos = passDtos();
        PassListResponse expectedResponse = passListResponse();

        Mockito.when(cartApplicationService.getCart(idul)).thenReturn(cartDtos);
        Mockito.when(passApiMapper.toPassListResponse(cartDtos)).thenReturn(expectedResponse);

        Response response = resource.getCart(idul);

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(expectedResponse, response.getEntity());

        Mockito.verify(cartApplicationService).getCart(idul);
        Mockito.verify(passApiMapper).toPassListResponse(cartDtos);
    }

    @Test
    void givenValidRequest_whenAddToCart_thenReturns200AndBody() {
        PassListRequest request = Mockito.mock(PassListRequest.class);
        List<PassDto> toAdd = passDtos();
        List<PassDto> updated = passDtos();
        PassListResponse mapped = passListResponse();

        Mockito.when(passApiMapper.toPassDtoList(request)).thenReturn(toAdd);
        Mockito.when(cartApplicationService.addToCart(idul, toAdd)).thenReturn(updated);
        Mockito.when(passApiMapper.toPassListResponse(updated)).thenReturn(mapped);

        Response response = resource.addToCart(idul, request);

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(mapped, response.getEntity());

        Mockito.verify(passApiMapper).toPassDtoList(request);
        Mockito.verify(cartApplicationService).addToCart(idul, toAdd);
        Mockito.verify(passApiMapper).toPassListResponse(updated);
    }

    @Test
    void givenPassId_whenRemoveFromCart_thenReturns204() {
        Response response = resource.removeFromCart(idul, PASS_ID);

        Assertions.assertEquals(204, response.getStatus());
        Assertions.assertNull(response.getEntity());

        Mockito.verify(cartApplicationService).removeFromCart(Mockito.eq(idul),
                Mockito.any(PassId.class));
        Mockito.verifyNoInteractions(passApiMapper);
    }

    @Test
    void whenClearCart_thenReturns204() {
        Response response = resource.clearCart(idul);

        Assertions.assertEquals(204, response.getStatus());
        Assertions.assertNull(response.getEntity());

        Mockito.verify(cartApplicationService).clearCart(idul);
        Mockito.verifyNoInteractions(passApiMapper);
    }

    private List<PassDto> passDtos() {
        return List.of(Mockito.mock(PassDto.class));
    }

    private PassListResponse passListResponse() {
        return Mockito.mock(PassListResponse.class);
    }
}
