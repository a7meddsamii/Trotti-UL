package ca.ulaval.glo4003.trotti.payment.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.order.domain.factories.PaymentMethodFactory;
import ca.ulaval.glo4003.trotti.payment.domain.security.DataCodec;
import ca.ulaval.glo4003.trotti.payment.domain.services.PaymentService;

public class PaymentDomainServiceLoader extends Bootstrapper {

    @Override
    public void load() {
        loadPaymentMethodFactory();
        this.resourceLocator.register(PaymentService.class, new PaymentService());
    }

    private void loadPaymentMethodFactory() {
        DataCodec dataCodec = this.resourceLocator.resolve(DataCodec.class);
        this.resourceLocator.register(PaymentMethodFactory.class,
                new PaymentMethodFactory(dataCodec));
    }
}
