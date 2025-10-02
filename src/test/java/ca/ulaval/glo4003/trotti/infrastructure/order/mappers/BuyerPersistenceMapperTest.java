package ca.ulaval.glo4003.trotti.infrastructure.order.mappers;

import static ca.ulaval.glo4003.trotti.fixtures.AccountFixture.*;

import ca.ulaval.glo4003.trotti.domain.order.Buyer;
import ca.ulaval.glo4003.trotti.domain.payment.utilities.SecuredString;
import ca.ulaval.glo4003.trotti.fixtures.BuyerFixture;
import ca.ulaval.glo4003.trotti.infrastructure.order.repository.records.CreditCardRecord;
import java.time.YearMonth;
import ca.ulaval.glo4003.trotti.infrastructure.order.repository.records.BuyerRecord;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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

        BuyerRecord dto = buyerMapper.toRecord(buyer);

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

        BuyerRecord dto = buyerMapper.toRecord(buyer);

        Assertions.assertEquals(buyer.getIdul(), dto.idul());
        Assertions.assertEquals(buyer.getName(), dto.name());
        Assertions.assertEquals(buyer.getEmail(), dto.email());
        Assertions.assertEquals(buyer.getCart().getPasses().size(), dto.cart().size());
        Assertions.assertNull(dto.paymentMethod());
    }

    @Test
    void givenBuyerRecordWithPaymentMethod_whenToBuyerDomain_thenReturnBuyer() {
        SecuredString securedString = Mockito.mock(SecuredString.class);
        CreditCardRecord creditCardRecord = new CreditCardRecord("John", securedString, YearMonth.of(2030, 12));
        BuyerRecord record = new BuyerRecord(AN_IDUL, A_NAME, AN_EMAIL, List.of(), creditCardRecord);

        Buyer result = buyerMapper.toDomain(record);

        Assertions.assertEquals(record.idul(), result.getIdul());
        Assertions.assertEquals(record.name(), result.getName());
        Assertions.assertEquals(record.email(), result.getEmail());
        Assertions.assertEquals(record.cart().size(), result.getCart().getPasses().size());
        Assertions.assertFalse(result.getPaymentMethod().isEmpty());
    }

    @Test
    void givenBuyerRecordWithoutPaymentMethod_whenToBuyerDomain_thenReturnWithoutPaymentMethod() {
        BuyerRecord record = new BuyerRecord(AN_IDUL, A_NAME, AN_EMAIL, List.of(), null);

        Buyer result = buyerMapper.toDomain(record);

        Assertions.assertEquals(record.idul(), result.getIdul());
        Assertions.assertEquals(record.name(), result.getName());
        Assertions.assertEquals(record.email(), result.getEmail());
        Assertions.assertEquals(record.cart().size(), result.getCart().getPasses().size());
        Assertions.assertTrue(result.getPaymentMethod().isEmpty());
    }
}
