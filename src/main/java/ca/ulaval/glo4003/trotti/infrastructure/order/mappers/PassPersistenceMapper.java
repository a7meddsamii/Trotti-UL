package ca.ulaval.glo4003.trotti.infrastructure.order.mappers;

import ca.ulaval.glo4003.trotti.domain.order.Pass;
import ca.ulaval.glo4003.trotti.infrastructure.order.repository.PassRecord;

public class PassPersistenceMapper {

    public Pass toDomain(PassRecord record) {
        return new Pass(record.maximumTravelingTime(), record.session(), record.billingFrequency(),
                record.idul(), record.id());
    }

    public PassRecord toRecord(Pass pass) {
        return new PassRecord(pass.getMaximumTravelingTime(), pass.getSession(),
                pass.getBillingFrequency(), pass.getId(), pass.getBuyerIdul());
    }
}
