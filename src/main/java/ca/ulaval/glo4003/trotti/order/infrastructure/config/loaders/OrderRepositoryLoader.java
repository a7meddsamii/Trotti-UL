package ca.ulaval.glo4003.trotti.order.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.commons.infrastructure.database.UserInMemoryDatabase;
import ca.ulaval.glo4003.trotti.config.loaders.Bootstrapper;
import ca.ulaval.glo4003.trotti.order.domain.repositories.BuyerRepository;
import ca.ulaval.glo4003.trotti.order.domain.repositories.PassRepository;
import ca.ulaval.glo4003.trotti.order.infrastructure.mappers.BuyerPersistenceMapper;
import ca.ulaval.glo4003.trotti.order.infrastructure.mappers.PassPersistenceMapper;
import ca.ulaval.glo4003.trotti.order.infrastructure.repositories.InMemoryBuyerRepository;
import ca.ulaval.glo4003.trotti.order.infrastructure.repositories.InMemoryPassRepository;

public class OrderRepositoryLoader extends Bootstrapper {

    @Override
    public void load() {
        loadPassRepository();
        loadBuyerRepository();
    }

    private void loadPassRepository() {
        PassPersistenceMapper passMapper =
                this.resourceLocator.resolve(PassPersistenceMapper.class);
        PassRepository passRepository = new InMemoryPassRepository(passMapper);
        this.resourceLocator.register(PassRepository.class, passRepository);
    }

    private void loadBuyerRepository() {
        UserInMemoryDatabase userInMemoryDatabase =
                this.resourceLocator.resolve(UserInMemoryDatabase.class);
        BuyerPersistenceMapper buyerMapper =
                this.resourceLocator.resolve(BuyerPersistenceMapper.class);

        BuyerRepository buyerRepository =
                new InMemoryBuyerRepository(userInMemoryDatabase, buyerMapper);
        this.resourceLocator.register(BuyerRepository.class, buyerRepository);
    }
}
