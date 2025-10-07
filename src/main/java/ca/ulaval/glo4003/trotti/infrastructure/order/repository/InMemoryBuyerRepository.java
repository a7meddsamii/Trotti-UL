package ca.ulaval.glo4003.trotti.infrastructure.order.repository;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.entities.buyer.Buyer;
import ca.ulaval.glo4003.trotti.domain.order.repositories.BuyerRepository;
import ca.ulaval.glo4003.trotti.infrastructure.order.mappers.BuyerPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.order.repository.records.BuyerRecord;
import ca.ulaval.glo4003.trotti.infrastructure.persistence.UserInMemoryDatabase;

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
    public void update(Buyer buyer) {
        BuyerRecord buyerRecord = buyerMapper.toRecord(buyer);
        this.databaseDriver.insertIntoBuyerTable(buyerRecord);
    }

    @Override
    public Buyer findByEmail(Email email) {
        BuyerRecord accountQuery = databaseDriver.selectFromBuyerTable(email);
        return buyerMapper.toDomain(accountQuery);
    }

    @Override
    public Buyer findByIdul(Idul idul) {
        BuyerRecord accountQuery = databaseDriver.selectFromBuyerTable(idul);
        return buyerMapper.toDomain(accountQuery);
    }
}
