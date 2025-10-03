package ca.ulaval.glo4003.trotti.api.order.exceptionmappers;

import ca.ulaval.glo4003.trotti.api.account.exceptionmappers.ExceptionResponseFactory;
import ca.ulaval.glo4003.trotti.domain.order.exceptions.CartException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CartExceptionMapper implements ExceptionMapper<CartException> {

    @Override
    public Response toResponse(CartException e) {
        return ExceptionResponseFactory.errorResponse(Response.Status.BAD_REQUEST, e.getMessage());
    }
}
