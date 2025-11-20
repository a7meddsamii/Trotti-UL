package ca.ulaval.glo4003.trotti.order.infrastructure.mappers;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.order.domain.entities.pass.Pass;
import ca.ulaval.glo4003.trotti.order.fixtures.PassFixture;
import ca.ulaval.glo4003.trotti.order.infrastructure.repositories.records.PassRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PassPersistenceMapperTest {

    private static final Idul AN_IDUL = Idul.from("a1234567");

    private PassPersistenceMapper mapper = new PassPersistenceMapper();

    @Test
    void givenPassRecord_whenToDomain_thenReturnPass() {
        PassRecord passRecord =
                new PassRecord(PassFixture.AN_PASSID, AN_IDUL, PassFixture.A_MAXIMUM_TRAVELING_TIME,
                        PassFixture.A_SESSION, PassFixture.A_BILLING_FREQUENCY);

        Pass pass = mapper.toDomain(passRecord);

        Assertions.assertEquals(passRecord.passId(), pass.getId());
        Assertions.assertEquals(passRecord.maximumDailyTravelTime(),
                pass.getMaximumTravelingTime());
        Assertions.assertEquals(passRecord.session(), pass.getSession());
        Assertions.assertEquals(passRecord.billingFrequency(), pass.getBillingFrequency());
        Assertions.assertEquals(passRecord.owner(), pass.getBuyerIdul());
    }

    @Test
    void givenPass_whenToRecord_thenReturnPassRecord() {
        Pass pass = new PassFixture().withIdul(AN_IDUL).build();

        PassRecord passRecord = mapper.toRecord(pass);

        Assertions.assertEquals(pass.getId(), passRecord.passId());
        Assertions.assertEquals(pass.getMaximumTravelingTime(),
                passRecord.maximumDailyTravelTime());
        Assertions.assertEquals(pass.getSession(), passRecord.session());
        Assertions.assertEquals(pass.getBillingFrequency(), passRecord.billingFrequency());
        Assertions.assertEquals(pass.getBuyerIdul(), passRecord.owner());
    }
}
