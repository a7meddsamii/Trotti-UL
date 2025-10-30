package ca.ulaval.glo4003.trotti.config.loaders;

import ca.ulaval.glo4003.trotti.account.api.mappers.AccountApiMapper;
import ca.ulaval.glo4003.trotti.account.domain.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.account.infrastructure.repositories.mappers.AccountPersistenceMapper;
import ca.ulaval.glo4003.trotti.commons.infrastructure.config.providers.sessions.SessionProvider;
import ca.ulaval.glo4003.trotti.order.api.mappers.OrderApiMapper;
import ca.ulaval.glo4003.trotti.order.api.mappers.PassApiMapper;
import ca.ulaval.glo4003.trotti.order.application.mappers.PassMapper;
import ca.ulaval.glo4003.trotti.order.application.mappers.TransactionMapper;
import ca.ulaval.glo4003.trotti.order.infrastructure.mappers.BuyerPersistenceMapper;
import ca.ulaval.glo4003.trotti.order.infrastructure.mappers.PassPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.api.mappers.TripApiMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.ScooterPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.StationPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.TravelerPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.TripPersistenceMapper;

public class MapperLoader extends Bootstrapper {
    @Override
    public void load() {
        loadPersistenceMappers();
        loadApplicationMappers();
        loadApiMappers();
    }

    private void loadPersistenceMappers() {
        this.resourceLocator.register(AccountPersistenceMapper.class,
                new AccountPersistenceMapper());
        this.resourceLocator.register(TravelerPersistenceMapper.class,
                new TravelerPersistenceMapper());
        this.resourceLocator.register(TripPersistenceMapper.class, new TripPersistenceMapper());
        this.resourceLocator.register(BuyerPersistenceMapper.class, new BuyerPersistenceMapper());
        this.resourceLocator.register(PassPersistenceMapper.class, new PassPersistenceMapper());
        this.resourceLocator.register(ScooterPersistenceMapper.class,
                new ScooterPersistenceMapper());
        this.resourceLocator.register(StationPersistenceMapper.class,
                new StationPersistenceMapper());
    }

    private void loadApplicationMappers() {
        this.resourceLocator.register(PassMapper.class, new PassMapper());
        this.resourceLocator.register(TransactionMapper.class, new TransactionMapper());
    }

    private void loadApiMappers() {
        PasswordHasher hasher = this.resourceLocator.resolve(PasswordHasher.class);
        SessionProvider sessionProvider = this.resourceLocator.resolve(SessionProvider.class);
        this.resourceLocator.register(AccountApiMapper.class, new AccountApiMapper(hasher));
        this.resourceLocator.register(OrderApiMapper.class, new OrderApiMapper());
        this.resourceLocator.register(PassApiMapper.class, new PassApiMapper(sessionProvider));
        this.resourceLocator.register(TripApiMapper.class, new TripApiMapper());
    }
}
