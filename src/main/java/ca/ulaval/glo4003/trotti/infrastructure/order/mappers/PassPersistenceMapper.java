package ca.ulaval.glo4003.trotti.infrastructure.order.mappers;

import ca.ulaval.glo4003.trotti.domain.order.entities.pass.Pass;
import ca.ulaval.glo4003.trotti.infrastructure.order.repositories.records.PassRecord;

public class PassPersistenceMapper {

    public Pass toDomain(PassRecord record) {
        return new Pass(record.maximumDailyTravelTime(), record.session(),
                record.billingFrequency(), record.passId(), record.owner());
    }

    public PassRecord toRecord(Pass pass) {
        return new PassRecord(pass.getId(), pass.getBuyerIdul(), pass.getMaximumTravelingTime(),
                pass.getSession(), pass.getBillingFrequency());
    }
}
