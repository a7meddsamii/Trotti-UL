package ca.ulaval.glo4003.trotti.application.order.mappers;

import ca.ulaval.glo4003.trotti.application.order.dto.PassDto;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.order.entities.pass.Pass;
import ca.ulaval.glo4003.trotti.domain.order.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.domain.order.values.MaximumDailyTravelTime;
import ca.ulaval.glo4003.trotti.domain.order.values.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PassMapperTest {

    private final PassMapper passMapper = new PassMapper();

    @Test
    void givenPass_whenToDto_thenReturnPassDtoWithSameFields() {
        Pass pass = Mockito.mock(Pass.class);
        MaximumDailyTravelTime maximumDailyTravelTime = Mockito.mock(MaximumDailyTravelTime.class);
        Session session = Mockito.mock(Session.class);
        BillingFrequency billingFrequency = Mockito.mock(BillingFrequency.class);
        Id id = Mockito.mock(Id.class);

        Mockito.when(pass.getMaximumTravelingTime()).thenReturn(maximumDailyTravelTime);
        Mockito.when(pass.getSession()).thenReturn(session);
        Mockito.when(pass.getBillingFrequency()).thenReturn(billingFrequency);
        Mockito.when(pass.getId()).thenReturn(id);

        PassDto dto = passMapper.toDto(pass);

        Assertions.assertEquals(maximumDailyTravelTime, dto.maximumDailyTravelTime());
        Assertions.assertEquals(session, dto.session());
        Assertions.assertEquals(billingFrequency, dto.billingFrequency());
        Assertions.assertEquals(id, dto.id());
    }
}
