package ca.ulaval.glo4003.trotti.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.api.account.mappers.AccountApiMapper;
import ca.ulaval.glo4003.trotti.api.order.mappers.OrderApiMapper;
import ca.ulaval.glo4003.trotti.api.order.mappers.PassApiMapper;
import ca.ulaval.glo4003.trotti.api.trip.mappers.TripApiMapper;
import ca.ulaval.glo4003.trotti.application.order.mappers.PassMapper;
import ca.ulaval.glo4003.trotti.application.order.mappers.TransactionMapper;
import ca.ulaval.glo4003.trotti.application.trip.mappers.RidePermitMapper;
import ca.ulaval.glo4003.trotti.domain.account.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.infrastructure.account.mappers.AccountPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.config.providers.SessionProvider;
import ca.ulaval.glo4003.trotti.infrastructure.order.mappers.BuyerPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.order.mappers.PassPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.trip.mappers.ScooterPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.trip.mappers.StationPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.trip.mappers.TravelerPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.trip.mappers.TripPersistenceMapper;

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
        this.resourceLocator.register(RidePermitMapper.class, new RidePermitMapper());
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
