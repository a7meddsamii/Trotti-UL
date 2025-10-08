package ca.ulaval.glo4003.trotti.infrastructure.order.mappers;

import ca.ulaval.glo4003.trotti.domain.order.entities.pass.Pass;
import ca.ulaval.glo4003.trotti.infrastructure.order.repositories.PassRecord;

public class PassPersistenceMapper {

    public Pass toDomain(PassRecord record) {
        return new Pass(record.maximumTravelingTime(), record.session(), record.billingFrequency(),
                record.id(), record.idul());
    }

    public PassRecord toRecord(Pass pass) {
        return new PassRecord(pass.getMaximumTravelingTime(), pass.getSession(),
                pass.getBillingFrequency(), pass.getId(), pass.getBuyerIdul());
    }
}
