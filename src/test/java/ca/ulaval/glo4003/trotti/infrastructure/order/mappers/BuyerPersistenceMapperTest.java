package ca.ulaval.glo4003.trotti.infrastructure.order.mappers;

import static ca.ulaval.glo4003.trotti.fixtures.AccountFixture.*;

import ca.ulaval.glo4003.trotti.domain.order.Buyer;
import ca.ulaval.glo4003.trotti.fixtures.BuyerFixture;
import ca.ulaval.glo4003.trotti.infrastructure.order.repository.record.BuyerRecord;
import java.util.List;
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
    void givenBuyerWithPaymentMethod_whenToRecord_thenReturnBuyerRecord() {
        Buyer buyer = buyerFixture.buildWithPaymentMethod();

        BuyerRecord dto = buyerMapper.toBuyerRecord(buyer);

        Assertions.assertEquals(buyer.getIdul(), dto.idul());
        Assertions.assertEquals(buyer.getName(), dto.name());
        Assertions.assertEquals(buyer.getEmail(), dto.email());
        Assertions.assertEquals(buyer.getCart().getPasses().size(), dto.cart().size());
        Assertions.assertEquals(buyer.getPaymentMethod().get().getSecuredString(),
                dto.paymentMethod().number());
    }

    @Test
    void givenBuyerWithoutPaymentMethod_whenToRecord_thenReturnBuyerRecordWithNullPaymentMethod() {
        Buyer buyer = buyerFixture.buildWithoutPaymentMethod();

        BuyerRecord dto = buyerMapper.toBuyerRecord(buyer);

        Assertions.assertEquals(buyer.getIdul(), dto.idul());
        Assertions.assertEquals(buyer.getName(), dto.name());
        Assertions.assertEquals(buyer.getEmail(), dto.email());
        Assertions.assertEquals(buyer.getCart().getPasses().size(), dto.cart().size());
        Assertions.assertNull(dto.paymentMethod());
    }

    @Test
    void givenBuyerRecordWithPaymentMethod_whenToBuyerDomain_thenReturnBuyer() {
        BuyerRecord record = new BuyerRecord(AN_IDUL, A_NAME, AN_EMAIL, List.of(), null);

        Buyer result = buyerMapper.toBuyerDomain(record);

        Assertions.assertEquals(record.idul(), result.getIdul());
        Assertions.assertEquals(record.name(), result.getName());
        Assertions.assertEquals(record.email(), result.getEmail());
        Assertions.assertEquals(record.cart().size(), result.getCart().getPasses().size());
        Assertions.assertTrue(result.getPaymentMethod().isEmpty());
    }

    @Test
    void givenBuyerRecordWithoutPaymentMethod_whenToBuyerDomain_thenReturnBuyerWithoutPaymentMethod() {
        BuyerRecord record = new BuyerRecord(AN_IDUL, A_NAME, AN_EMAIL, List.of(), null);

        Buyer result = buyerMapper.toBuyerDomain(record);

        Assertions.assertEquals(record.idul(), result.getIdul());
        Assertions.assertEquals(record.name(), result.getName());
        Assertions.assertEquals(record.email(), result.getEmail());
        Assertions.assertEquals(record.cart().size(), result.getCart().getPasses().size());
        Assertions.assertTrue(result.getPaymentMethod().isEmpty());
    }
}
