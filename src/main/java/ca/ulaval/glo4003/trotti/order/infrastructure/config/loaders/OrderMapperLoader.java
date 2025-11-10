package ca.ulaval.glo4003.trotti.order.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.commons.infrastructure.gateways.sessions.SessionProvider;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.order.api.mappers.OrderApiMapper;
import ca.ulaval.glo4003.trotti.order.api.mappers.PassApiMapper;
import ca.ulaval.glo4003.trotti.order.application.PassMapper;
import ca.ulaval.glo4003.trotti.order.application.TransactionMapper;
import ca.ulaval.glo4003.trotti.order.infrastructure.mappers.BuyerPersistenceMapper;
import ca.ulaval.glo4003.trotti.order.infrastructure.mappers.PassPersistenceMapper;

public class OrderMapperLoader extends Bootstrapper {

    @Override
    public void load() {
        loadPersistenceMappers();
        loadApplicationMappers();
        loadApiMappers();
    }

    private void loadPersistenceMappers() {
        this.resourceLocator.register(BuyerPersistenceMapper.class, new BuyerPersistenceMapper());
        this.resourceLocator.register(PassPersistenceMapper.class, new PassPersistenceMapper());
    }

    private void loadApplicationMappers() {
        this.resourceLocator.register(PassMapper.class, new PassMapper());
        this.resourceLocator.register(TransactionMapper.class, new TransactionMapper());
    }

    private void loadApiMappers() {
        SessionProvider sessionProvider = this.resourceLocator.resolve(SessionProvider.class);
        this.resourceLocator.register(OrderApiMapper.class, new OrderApiMapper());
        this.resourceLocator.register(PassApiMapper.class, new PassApiMapper(sessionProvider));
    }
}
