package ca.ulaval.glo4003.trotti.infrastructure.order.mappers;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.entities.pass.Pass;
import ca.ulaval.glo4003.trotti.fixtures.PassFixture;
import ca.ulaval.glo4003.trotti.infrastructure.order.repositories.PassRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PassPersistenceMapperTest {

    private static final Idul AN_IDUL = Idul.from("a1234567");

    private PassPersistenceMapper mapper = new PassPersistenceMapper();

    @Test
    void givenPassRecord_whenToDomain_thenReturnPass() {
        PassRecord passRecord = new PassRecord(PassFixture.A_MAXIMUM_TRAVELING_TIME,
                PassFixture.A_SESSION, PassFixture.A_BILLING_FREQUENCY, PassFixture.AN_ID, AN_IDUL);

        Pass pass = mapper.toDomain(passRecord);

        Assertions.assertEquals(passRecord.id(), pass.getId());
        Assertions.assertEquals(passRecord.maximumTravelingTime(), pass.getMaximumTravelingTime());
        Assertions.assertEquals(passRecord.session(), pass.getSession());
        Assertions.assertEquals(passRecord.billingFrequency(), pass.getBillingFrequency());
        Assertions.assertEquals(passRecord.idul(), pass.getBuyerIdul());
    }

    @Test
    void givenPass_whenToRecord_thenReturnPassRecord() {
        Pass pass = new PassFixture().withIdul(AN_IDUL).build();

        PassRecord passRecord = mapper.toRecord(pass);

        Assertions.assertEquals(pass.getId(), passRecord.id());
        Assertions.assertEquals(pass.getMaximumTravelingTime(), passRecord.maximumTravelingTime());
        Assertions.assertEquals(pass.getSession(), passRecord.session());
        Assertions.assertEquals(pass.getBillingFrequency(), passRecord.billingFrequency());
        Assertions.assertEquals(pass.getBuyerIdul(), passRecord.idul());
    }
}
