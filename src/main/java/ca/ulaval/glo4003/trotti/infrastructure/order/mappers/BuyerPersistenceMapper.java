package ca.ulaval.glo4003.trotti.infrastructure.order.mappers;

import ca.ulaval.glo4003.trotti.domain.order.Buyer;
import ca.ulaval.glo4003.trotti.infrastructure.order.repository.BuyerRecord;

public class BuyerPersistenceMapper {

    public BuyerRecord toDTO(Buyer buyer) {
        return new BuyerRecord(buyer.getIdul(), buyer.getName(), buyer.getEmail(), buyer.getCart(),
                buyer.getPaymentMethod());
    }

    public Buyer toEntity(BuyerRecord buyerFound) {
        return buyerFound.paymentMethod()
                .map(paymentMethod -> new Buyer(buyerFound.idul(), buyerFound.name(),
                        buyerFound.email(), buyerFound.cart(), paymentMethod))
                .orElseGet(() -> new Buyer(buyerFound.idul(), buyerFound.name(), buyerFound.email(),
                        buyerFound.cart()));
    }
}
