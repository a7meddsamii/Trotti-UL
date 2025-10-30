package ca.ulaval.glo4003.trotti.config.loaders;

import ca.ulaval.glo4003.trotti.account.domain.factories.AccountFactory;
import ca.ulaval.glo4003.trotti.order.domain.factories.OrderFactory;
import ca.ulaval.glo4003.trotti.order.domain.factories.PassFactory;
import ca.ulaval.glo4003.trotti.order.domain.factories.PaymentMethodFactory;
import ca.ulaval.glo4003.trotti.payment.domain.security.DataCodec;
import ca.ulaval.glo4003.trotti.trip.domain.factories.ScooterFactory;
import ca.ulaval.glo4003.trotti.trip.domain.factories.StationFactory;
import java.time.Clock;

public class FactoryLoader extends Bootstrapper {
    @Override
    public void load() {
        loadPaymentMethodFactory();
        loadOrderFactory();
        loadAccountFactory();
        loadPassFactory();
        loadStationFactory();
        loadScooterFactory();
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

    private void loadStationFactory() {
        this.resourceLocator.register(StationFactory.class, new StationFactory());
    }

    private void loadScooterFactory() {
        this.resourceLocator.register(ScooterFactory.class, new ScooterFactory());
    }
}
