package ca.ulaval.glo4003.trotti.order.api.exceptionmappers;

import ca.ulaval.glo4003.trotti.commons.api.exceptionmappers.ExceptionResponseFactory;
import ca.ulaval.glo4003.trotti.order.domain.exceptions.CartException;
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
