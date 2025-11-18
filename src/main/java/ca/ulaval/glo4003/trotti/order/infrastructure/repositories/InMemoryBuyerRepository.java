package ca.ulaval.glo4003.trotti.order.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.infrastructure.database.UserInMemoryDatabase;
import ca.ulaval.glo4003.trotti.order.domain.entities.buyer.Buyer;
import ca.ulaval.glo4003.trotti.order.domain.repositories.BuyerRepository;
import ca.ulaval.glo4003.trotti.order.infrastructure.mappers.BuyerPersistenceMapper;
import ca.ulaval.glo4003.trotti.order.infrastructure.repositories.records.BuyerRecord;

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
