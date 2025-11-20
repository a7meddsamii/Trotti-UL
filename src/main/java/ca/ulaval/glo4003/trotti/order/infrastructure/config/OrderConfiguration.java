package ca.ulaval.glo4003.trotti.order.infrastructure.config;

import ca.ulaval.glo4003.trotti.config.Configuration;
import ca.ulaval.glo4003.trotti.order.infrastructure.config.loaders.*;

public class OrderConfiguration extends Configuration {

    protected static Configuration instance;

    private OrderConfiguration() {
        super();
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new OrderConfiguration();
        }

        return instance;
    }

    @Override
    protected void load() {
        new OrderForeignServiceLoader().load();
        new OrderFactoryLoader().load();
        new OrderMapperLoader().load();
        new OrderRepositoryLoader().load();
        new OrderDomainServiceLoader().load();
        new OrderApplicationServiceLoader().load();
        new OrderResourceLoader().load();
    }
}
