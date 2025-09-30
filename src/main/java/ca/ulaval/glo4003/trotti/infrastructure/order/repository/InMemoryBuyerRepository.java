package ca.ulaval.glo4003.trotti.infrastructure.order.repository;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.Buyer;
import ca.ulaval.glo4003.trotti.domain.order.repository.BuyerRepository;
import ca.ulaval.glo4003.trotti.infrastructure.order.mappers.BuyerPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.persistence.UserInMemoryDatabase;
import java.util.Optional;

public class InMemoryBuyerRepository implements BuyerRepository {
    private final UserInMemoryDatabase databaseDriver;
    private final BuyerPersistenceMapper buyerMapper;

    public InMemoryBuyerRepository(
            UserInMemoryDatabase databaseDriver,
            BuyerPersistenceMapper buyerMapper) {
        this.databaseDriver = databaseDriver;
        this.buyerMapper = buyerMapper;
    }

    @Override
    public void save(Buyer buyer) {
        BuyerRecord buyerRecord = buyerMapper.toDTO(buyer);
        this.databaseDriver.insertIntoBuyerTable(buyerRecord);
    }

    @Override
    public Optional<Buyer> findByEmail(Email email) {
        Optional<BuyerRecord> accountQuery = databaseDriver.selectFromBuyerTable(email);
        return accountQuery.map(this.buyerMapper::toEntity);
    }

    @Override
    public Optional<Buyer> findByIdul(Idul idul) {
        Optional<BuyerRecord> accountQuery = databaseDriver.selectFromBuyerTable(idul);
        return accountQuery.map(this.buyerMapper::toEntity);
    }
}
