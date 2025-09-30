package ca.ulaval.glo4003.trotti.infrastructure.order.mappers;

import static ca.ulaval.glo4003.trotti.fixtures.AccountFixture.*;

import ca.ulaval.glo4003.trotti.domain.order.Buyer;
import ca.ulaval.glo4003.trotti.domain.order.Cart;
import ca.ulaval.glo4003.trotti.fixtures.BuyerFixture;
import ca.ulaval.glo4003.trotti.fixtures.CreditCardFixture;
import ca.ulaval.glo4003.trotti.infrastructure.order.repository.BuyerRecord;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BuyerPersistenceMapperTest {

    private BuyerPersistenceMapper buyerMapper;
    private BuyerFixture buyerFixture;

    @BeforeEach
    void setup() {
        buyerMapper = new BuyerPersistenceMapper();
        buyerFixture = new BuyerFixture();
    }

    @Test
    void givenBuyerWithPaymentMethod_whenToDTO_thenReturnBuyerRecord() {
        Buyer buyer = buyerFixture.buildWithoutPaymentMethod();

        BuyerRecord dto = buyerMapper.toDTO(buyer);

        Assertions.assertEquals(buyer.getIdul(), dto.idul());
        Assertions.assertEquals(buyer.getName(), dto.name());
        Assertions.assertEquals(buyer.getEmail(), dto.email());
        Assertions.assertEquals(buyer.getCart(), dto.cart());
        Assertions.assertEquals(buyer.getPaymentMethod(), dto.paymentMethod());
    }

    @Test
    void givenBuyerWithoutPaymentMethod_whenToDTO_thenReturnBuyerRecordWithEmptyPaymentMethod() {
        Buyer buyer = buyerFixture.buildWithoutPaymentMethod();

        BuyerRecord dto = buyerMapper.toDTO(buyer);

        Assertions.assertEquals(buyer.getIdul(), dto.idul());
        Assertions.assertEquals(buyer.getName(), dto.name());
        Assertions.assertEquals(buyer.getEmail(), dto.email());
        Assertions.assertEquals(buyer.getCart(), dto.cart());
        Assertions.assertTrue(dto.paymentMethod().isEmpty());
    }

    @Test
    void givenBuyerRecordWithPaymentMethod_whenToEntity_thenReturnBuyer() {
        BuyerRecord record = new BuyerRecord(AN_IDUL, A_NAME, AN_EMAIL, new Cart(),
                Optional.of(new CreditCardFixture().build()));

        Buyer result = buyerMapper.toEntity(record);

        Assertions.assertEquals(record.idul(), result.getIdul());
        Assertions.assertEquals(record.name(), result.getName());
        Assertions.assertEquals(record.email(), result.getEmail());
        Assertions.assertEquals(record.cart(), result.getCart());
        Assertions.assertEquals(record.paymentMethod(), result.getPaymentMethod());
    }

    @Test
    void givenBuyerRecordWithoutPaymentMethod_whenToEntity_thenReturnBuyerWithoutPaymentMethod() {
        BuyerRecord record =
                new BuyerRecord(AN_IDUL, A_NAME, AN_EMAIL, new Cart(), Optional.empty());

        Buyer result = buyerMapper.toEntity(record);

        Assertions.assertEquals(record.idul(), result.getIdul());
        Assertions.assertEquals(record.name(), result.getName());
        Assertions.assertEquals(record.email(), result.getEmail());
        Assertions.assertEquals(record.cart(), result.getCart());
        Assertions.assertTrue(result.getPaymentMethod().isEmpty());
    }
}
