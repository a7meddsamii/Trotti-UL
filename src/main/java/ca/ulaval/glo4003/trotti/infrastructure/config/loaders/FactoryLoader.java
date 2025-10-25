package ca.ulaval.glo4003.trotti.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.domain.account.factories.AccountFactory;
import ca.ulaval.glo4003.trotti.domain.commons.payment.security.DataCodec;
import ca.ulaval.glo4003.trotti.domain.order.factories.OrderFactory;
import ca.ulaval.glo4003.trotti.domain.order.factories.PassFactory;
import ca.ulaval.glo4003.trotti.domain.order.factories.PaymentMethodFactory;
import java.time.Clock;

public class FactoryLoader extends Bootstrapper {
    @Override
    public void load() {
        loadPaymentMethodFactory();
        loadOrderFactory();
        loadAccountFactory();
        loadPassFactory();
    }

    private void loadPaymentMethodFactory() {
        DataCodec dataCodec = this.resourceLocator.resolve(DataCodec.class);
        this.resourceLocator.register(PaymentMethodFactory.class,
                new PaymentMethodFactory(dataCodec));
    }

    private void loadOrderFactory() {
        this.resourceLocator.register(OrderFactory.class, new OrderFactory());
    }

    private void loadAccountFactory() {
        Clock clock = this.resourceLocator.resolve(Clock.class);
        this.resourceLocator.register(AccountFactory.class, new AccountFactory(clock));
    }

    private void loadPassFactory() {
        this.resourceLocator.register(PassFactory.class, new PassFactory());
    }
}
